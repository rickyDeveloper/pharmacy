package com.pharmacy.repository;

import com.pharmacy.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Transactional
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByUserId(Long userId);

}
