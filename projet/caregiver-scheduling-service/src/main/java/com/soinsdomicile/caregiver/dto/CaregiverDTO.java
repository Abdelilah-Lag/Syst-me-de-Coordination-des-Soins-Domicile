package com.soinsdomicile.caregiver.dto;

import com.soinsdomicile.caregiver.model.Caregiver;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CaregiverDTO {
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    private Caregiver.TypeSoignant typeSoignant;
    private String telephone;
    private String email;
    private String adresseBase;
    private Double latitudeBase;
    private Double longitudeBase;
    private List<String> competences;
    private Integer capaciteVisitesParJour;
    private Caregiver.StatutSoignant statut;
}
