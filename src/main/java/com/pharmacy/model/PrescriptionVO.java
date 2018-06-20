package com.pharmacy.model;

import lombok.Data;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
@Data
public class PrescriptionVO {
    private Long id;

    private Long userId;

    private String text;
}
