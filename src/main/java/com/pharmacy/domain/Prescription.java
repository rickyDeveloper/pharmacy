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
@Table(name="prescriptions")
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "text")
    private String text;

}
