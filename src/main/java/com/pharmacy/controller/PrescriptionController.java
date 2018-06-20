package com.pharmacy.controller;

import com.pharmacy.cache.ViewPrescriptionCache;
import com.pharmacy.domain.Prescription;
import com.pharmacy.domain.User;
import com.pharmacy.domain.ViewPrescription;
import com.pharmacy.enums.ViewPrescriptionStatus;
import com.pharmacy.model.PrescriptionVO;
import com.pharmacy.repository.PrescriptionRepository;
import com.pharmacy.repository.UserRepository;
import com.pharmacy.repository.ViewPrescriptionRepository;
import com.pharmacy.security.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Controller
@Slf4j
public class PrescriptionController {


    @Autowired
    private UserDetails userDetails;


    @Autowired
    private PrescriptionRepository prescriptionRepository;


    @Autowired
    private ViewPrescriptionRepository viewPrescriptionRepository;


    @Autowired
    private UserRepository userRepository;




    @RequestMapping(value = "/prescriptions", method = RequestMethod.GET)
    public ModelAndView getAllPrescriptions() {
        ModelAndView modelAndView = new ModelAndView("prescriptions");
        modelAndView.addObject("prescriptions", prescriptionRepository.findAll());
        return modelAndView;
    }


    @RequestMapping(value = "/prescriptions/users/{userId}", method = RequestMethod.GET)
    public ModelAndView getPrescriptionsByUserId(@PathVariable("userId") Long userId) {

        ModelAndView modelAndView = new ModelAndView("prescriptions");

        List<PrescriptionVO> prescriptionVOs = Collections.emptyList();

        List<Prescription> prescriptions = prescriptionRepository.findByUserId(userId);

        if(prescriptions != null) {

            log.info("Logged in user is {}",
                    userDetails.getLoggedInUserName()
            );

            User user = userRepository.findByUserName(userDetails.getLoggedInUserName());

            if(user != null) {
                Map<Long,ViewPrescription> viewPrescriptionMap =  ViewPrescriptionCache.getINSTANCE().getPrescriptionRequestByUserId(user.getId());
                    log.info("Found {} prescription for user is {}",viewPrescriptionMap.size(),
                            user.getId() );

                prescriptionVOs = prescriptions.stream().map(p -> {
                          PrescriptionVO prescriptionVO = new PrescriptionVO();
                          prescriptionVO.setId(p.getId());
                          prescriptionVO.setUserId(p.getUserId());
                          prescriptionVO.setStatus(ViewPrescriptionStatus.NOT_REQUESTED.name()); //DEFAULT VALUE
                          viewPrescriptionMap.values().stream().filter(vp -> vp.getPrescriptionId().equals(p.getId())).forEach(vp -> {
                              if(ViewPrescriptionStatus.ACCEPTED.name().equals(vp.getStatus())) {
                                  prescriptionVO.setText(p.getText());
                              }
                              prescriptionVO.setStatus(vp.getStatus());
                          });

                            log.info("Prescription has id {} and status = {}",prescriptionVO.getId(),
                                    prescriptionVO.getStatus());

                          return prescriptionVO;
                  }).collect(Collectors.toList());
            }
        }


        modelAndView.addObject("prescriptions", prescriptionVOs);
        return modelAndView;

    }


    @RequestMapping(value = "/prescriptions/requests/{id}", method = RequestMethod.GET)
    public ModelAndView requestAccessToPrescription(@PathVariable("id") Long id) {
        log.info("Registering access request for user {} for presciption id {}",
                userDetails.getLoggedInUserName(), id
        );

        User user = userRepository.findByUserName(userDetails.getLoggedInUserName());

        ViewPrescription viewPrescription = ViewPrescriptionCache.getINSTANCE().getPrescriptionRequest(id, user.getId());

        if(viewPrescription != null) {
            log.info("Found approval request with id={}, presId={} and status={}", viewPrescription.getUserId(), viewPrescription.getPrescriptionId(), viewPrescription.getStatus());
            viewPrescription.setStatus(ViewPrescriptionStatus.PENDING.name());
        } else {
            viewPrescription = new ViewPrescription();
            Random rand = new Random();
            viewPrescription.setId(rand.nextInt());
            viewPrescription.setPrescriptionId(id);
            viewPrescription.setUserId(user.getId());
            viewPrescription.setStatus(ViewPrescriptionStatus.PENDING.name());
            log.info("Creating new approval request with id={}, presId={} and status={}", viewPrescription.getUserId(), viewPrescription.getPrescriptionId(), viewPrescription.getStatus());
        }

        ViewPrescriptionCache.getINSTANCE().addPrescriptionRequest(viewPrescription);

        ModelAndView modelAndView = new ModelAndView("approval");
        modelAndView.addObject("approval", viewPrescription);

        return modelAndView;
    }
}
