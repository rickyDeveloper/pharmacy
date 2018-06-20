package com.pharmacy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Embeddable
@Getter
@Setter
public class ViewPrescriptionPK implements Serializable {

    @Column(name = "id")
    private Long id;

    @Column(name = "user_Id")
    private Long userId;

}
