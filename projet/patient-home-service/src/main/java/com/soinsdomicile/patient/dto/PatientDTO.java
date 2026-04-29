package com.soinsdomicile.patient.dto;

import com.soinsdomicile.patient.model.Patient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    private String telephone;
    private String email;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    private Double latitude;
    private Double longitude;

    private Patient.NiveauDependance niveauDependance;
    private String pathologies;
    private String besoinsSpecifiques;
    private String contraintes;
    private String nomAidant;
    private String telephoneAidant;
    private Patient.StatutPatient statut;
}
