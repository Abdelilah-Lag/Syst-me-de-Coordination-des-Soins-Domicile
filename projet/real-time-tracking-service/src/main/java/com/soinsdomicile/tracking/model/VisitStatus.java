package com.soinsdomicile.tracking.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitStatus {
    private String visitId;
    private Long patientId;
    private Long caregiverId;
    private StatutVisite statut;
    private Double caregiverLatitude;
    private Double caregiverLongitude;
    private LocalDateTime heureDebutReelle;
    private LocalDateTime heureFinReelle;
    private Integer retardMinutes;
    private String notes;
    private LocalDateTime lastUpdated;

    public enum StatutVisite {
        PLANIFIEE, EN_ROUTE, ARRIVEE, EN_COURS, TERMINEE, ANNULEE, INCIDENT
    }
}
