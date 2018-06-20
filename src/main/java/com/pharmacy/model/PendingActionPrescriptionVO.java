package com.pharmacy.model;

import lombok.Data;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Data
public class PendingActionPrescriptionVO extends PrescriptionVO{

    private String userName;

    private String firstName;

    private String lastName;

    private String role;

    private String requesterId;


}
