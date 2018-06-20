package com.pharmacy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Entity
@Getter
@Setter
@Table(name="view_prescriptions")
public class ViewPrescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "prescription_id")
    private Long prescriptionId;

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "status")
    private String status;

}
