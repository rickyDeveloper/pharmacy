package com.pharmacy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    @EmbeddedId
    private ViewPrescriptionPK pk;


    @Column(name = "status")
    private int status;

}
