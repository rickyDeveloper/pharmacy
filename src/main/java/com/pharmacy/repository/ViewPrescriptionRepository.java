package com.pharmacy.repository;

import com.pharmacy.domain.ViewPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Transactional
public interface ViewPrescriptionRepository extends JpaRepository<ViewPrescription, Long> {

    @Query(value="select vp.id, vp.prescription_id, vp.user_id, vp.status FROM view_prescriptions vp where vp.user_id=?1",nativeQuery=true)
    List<ViewPrescription> findByUserId(Long userId);

    @Query(value="select vp.id, vp.prescription_id, vp.user_id, vp.status FROM view_prescriptions vp where vp.user_id=?2 and vp.prescription_id=?1",nativeQuery=true)
    ViewPrescription findByPrescriptionIdAndUserId(Long prescriptionId, Long userId);

   /* @Modifying
    @Query("INSERT INTO view_prescriptions ( id, prescription_id, user_id, status) VALUES (1,1,2,'PENDING')")
    public int insertRequest()
*/
}
