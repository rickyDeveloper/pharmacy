package com.pharmacy.cache;

import com.pharmacy.domain.ViewPrescription;
import com.pharmacy.enums.ViewPrescriptionStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Slf4j
public class ViewPrescriptionCache {

    private static final ViewPrescriptionCache INSTANCE = new ViewPrescriptionCache();

    private final ConcurrentHashMap<Long, Map<Long,ViewPrescription>> requests ;

    private ViewPrescriptionCache() {
        this.requests = new ConcurrentHashMap<>();
    }


    public static ViewPrescriptionCache getINSTANCE(){
        return INSTANCE;
    }


    public Map<Long,ViewPrescription> getPrescriptionRequestByUserId(Long userId) {
        Map<Long,ViewPrescription> viewPrescriptionMap = Collections.emptyMap();

        if(requests.containsKey(userId)) {
            log.info("getPrescriptionRequestByUserId Found existing map for userId {}", userId);

            viewPrescriptionMap = requests.get(userId);
        } else {
            log.info("getPrescriptionRequestByUserId No record found for userId {}", userId);
        }

        return viewPrescriptionMap;
    }


    public ViewPrescription getPrescriptionRequest(Long presId, Long userId) {
        ViewPrescription viewPrescription = null;

        if(requests.containsKey(userId)) {
            log.info("getPrescriptionRequest Found existing map for userId {}", userId);
            if(requests.get(userId).containsKey(presId)) {
                log.info("getPrescriptionRequest Found existing map for presId {}", presId);

                viewPrescription = requests.get(userId).get(presId);
            }
        }

        return viewPrescription;
    }

    public ViewPrescription addPrescriptionRequest(ViewPrescription viewPrescription) {

        if(requests.containsKey(viewPrescription.getUserId())) {
            log.info("addPrescriptionRequest Found existing map for userId {}", viewPrescription.getUserId());
            requests.get(viewPrescription.getUserId()).put(viewPrescription.getPrescriptionId(),viewPrescription);
        } else {
            Map<Long,ViewPrescription> map = new ConcurrentHashMap<>();
            map.put(viewPrescription.getPrescriptionId(),viewPrescription);
            log.info("addPrescriptionRequest Adding new map for userId {}", viewPrescription.getUserId());
            requests.put(viewPrescription.getUserId(), map);
        }

        return viewPrescription;
    }

    public List<ViewPrescription> getUserPrescriptionByState(Long prescriptionId, ViewPrescriptionStatus status) {

        log.info("getUserPrescriptionByState Trying to lookup data for prescriptionId={} and status={}", prescriptionId, status.name());

        List<ViewPrescription> prescriptions = requests.values().stream()
                    .filter(map -> map.containsKey(prescriptionId))
                    .filter(map -> status.name().equals(map.get(prescriptionId).getStatus()))
                .map(map ->  map.get(prescriptionId)).collect(Collectors.toList());

        return prescriptions;
    }


    public boolean approvePrescriptionRequest(Long presId, Long userId, ViewPrescriptionStatus status) {

        if(requests.containsKey(userId)) {
            log.info("approvePrescriptionRequest Found existing map for userId {}", userId);
            ViewPrescription viewPrescription = requests.get(userId).get(presId);

            if(viewPrescription != null) {
                log.info("approvePrescriptionRequest updating status for presId {}", presId);
                viewPrescription.setStatus(status.name());
            } else {
                log.info("approvePrescriptionRequest didn't find presId {}", presId);
            }

            return true;

        } else {
            log.info("Pres request={} not found for presId {}", presId, userId);
            return false;

        }

    }

}
