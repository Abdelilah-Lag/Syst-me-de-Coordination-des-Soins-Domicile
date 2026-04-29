package com.soinsdomicile.visit.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VisitRequest {
    private Long patientId;
    private Long caregiverId;
    private LocalDate date;
    private LocalTime heurePreferee;
    private Integer dureeMinutes;
    private Double patientLatitude;
    private Double patientLongitude;
    private String typeVisite;
}
