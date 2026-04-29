package com.soinsdomicile.visit.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptimizedRoute {
    private Long caregiverId;
    private String caregiverNom;
    private LocalDate date;
    private List<VisitStop> stops;
    private Double distanceTotaleKm;
    private Integer dureeEstimeeMinutes;
    private Integer nombreVisites;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VisitStop {
        private int ordre;
        private Long patientId;
        private String patientNom;
        private String adresse;
        private Double latitude;
        private Double longitude;
        private String heureArriveeEstimee;
        private Integer dureeMinutes;
        private Double distanceDepuisPrecedentKm;
    }
}
