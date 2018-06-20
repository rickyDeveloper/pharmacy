package com.pharmacy.controller;

import com.pharmacy.domain.Prescription;
import com.pharmacy.domain.User;
import com.pharmacy.domain.ViewPrescription;
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
                  List<ViewPrescription> viewPrescriptions =  viewPrescriptionRepository.findByUserId(userId);
                    log.info("Found {} prescription for user is {}",viewPrescriptions.size(),
                        userDetails.getLoggedInUserName() );
            }
        }


        modelAndView.addObject("prescriptions", prescriptionVOs);
        return modelAndView;

    }
}
