package com.pharmacy.repository;

import com.pharmacy.domain.ViewPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
public interface ViewPrescriptionRepository extends JpaRepository<ViewPrescription, Long> {


    @Query(value="select vp.id, vp.user_id, vp.status FROM view_prescriptions vp where vp.user_id=?1",nativeQuery=true)
    List<ViewPrescription> findByUserId(Long userId);
}
