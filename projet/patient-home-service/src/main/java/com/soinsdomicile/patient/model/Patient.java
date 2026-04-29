package com.soinsdomicile.patient.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    private String telephone;
    private String email;

    @Column(nullable = false)
    private String adresse;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private NiveauDependance niveauDependance;

    @Column(columnDefinition = "TEXT")
    private String pathologies;

    @Column(columnDefinition = "TEXT")
    private String besoinsSpecifiques;

    @Column(columnDefinition = "TEXT")
    private String contraintes;

    private String nomAidant;
    private String telephoneAidant;

    @Column(nullable = false)
    private LocalDateTime dateInscription;

    @Enumerated(EnumType.STRING)
    private StatutPatient statut;

    @PrePersist
    public void prePersist() {
        this.dateInscription = LocalDateTime.now();
        if (this.statut == null) this.statut = StatutPatient.ACTIF;
    }

    public enum NiveauDependance {
        AUTONOME, SEMI_DEPENDANT, DEPENDANT, TRES_DEPENDANT
    }

    public enum StatutPatient {
        ACTIF, INACTIF, HOSPITALISE, DECEDE
    }
}
