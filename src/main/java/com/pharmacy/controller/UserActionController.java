package com.pharmacy.controller;

import com.pharmacy.cache.ViewPrescriptionCache;
import com.pharmacy.domain.Prescription;
import com.pharmacy.domain.User;
import com.pharmacy.domain.ViewPrescription;
import com.pharmacy.enums.UserRoles;
import com.pharmacy.enums.ViewPrescriptionStatus;
import com.pharmacy.model.PendingActionPrescriptionVO;
import com.pharmacy.repository.PrescriptionRepository;
import com.pharmacy.repository.UserRepository;
import com.pharmacy.security.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by vikasnaiyar on 20/06/18.
 */

@Controller
@Slf4j
public class UserActionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetails userDetails;

    @Autowired
    private PrescriptionRepository prescriptionRepository;


    @RequestMapping(value = "/actions", method = RequestMethod.GET)
    public ModelAndView getActionItems() {
        log.info("Looking for user={}", userDetails.getLoggedInUserName());
        User user = userRepository.findByUserName(userDetails.getLoggedInUserName());

        ModelAndView modelAndView = null;

        if(user == null) {
            modelAndView = new ModelAndView("403");
        } else {
            log.info("User user={} has role={}", user.getUserName(), user.getRole());
            if(UserRoles.PATIENT.name().equals(user.getRole())) {

               final List<PendingActionPrescriptionVO> pendingActionPrescriptionVOs = new ArrayList<>();

               List<Prescription> prescriptions = prescriptionRepository.findByUserId(user.getId());

                log.info("Found prescriptions count={} has user={}", prescriptions.size(), user.getUserName());

                prescriptions.stream().forEach(p -> {
                   List<ViewPrescription> pendingActions = ViewPrescriptionCache.getINSTANCE().getUserPrescriptionByState(p.getId(), ViewPrescriptionStatus.PENDING);

                    log.info("Found pendingActions count={} has user={}", pendingActions.size());

                    List<PendingActionPrescriptionVO> list = pendingActions.stream().map(pa -> {
                       Optional<User> owner = userRepository.findById(pa.getUserId());
                        PendingActionPrescriptionVO vo = new PendingActionPrescriptionVO();

                        if(owner.isPresent()) {
                           User temp = owner.get();
                           log.info("Creating record for prescriptions={} has user={}", pa.getId(), temp.getUserName());
                           vo.setUserId(temp.getId());
                           vo.setFirstName(temp.getFirstName());
                           vo.setLastName(temp.getLastName());
                           vo.setUserName(temp.getUserName());
                           vo.setId(p.getId());
                           vo.setRole(temp.getRole());
                           vo.setText(p.getText());
                       }
                        return vo;
                   }).collect(Collectors.toList());

                    pendingActionPrescriptionVOs.addAll(list);

               });
                modelAndView = new ModelAndView("patient");
                modelAndView.addObject("prescriptions", pendingActionPrescriptionVOs);
           } else {
                modelAndView = new ModelAndView("users");
                modelAndView.addObject("users", userRepository.findAll());
            }
        }

        return modelAndView;
    }

}
