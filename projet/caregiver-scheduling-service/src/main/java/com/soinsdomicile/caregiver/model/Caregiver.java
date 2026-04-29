package com.soinsdomicile.caregiver.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "caregivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Caregiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Enumerated(EnumType.STRING)
    private TypeSoignant typeSoignant;

    private String telephone;
    private String email;
    private String adresseBase;
    private Double latitudeBase;
    private Double longitudeBase;

    @ElementCollection
    @CollectionTable(name = "caregiver_competences", joinColumns = @JoinColumn(name = "caregiver_id"))
    @Column(name = "competence")
    private List<String> competences;

    private Integer capaciteVisitesParJour;

    @Enumerated(EnumType.STRING)
    private StatutSoignant statut;

    @Column(nullable = false)
    private LocalDateTime dateEmbauche;

    @PrePersist
    public void prePersist() {
        this.dateEmbauche = LocalDateTime.now();
        if (this.statut == null) this.statut = StatutSoignant.DISPONIBLE;
        if (this.capaciteVisitesParJour == null) this.capaciteVisitesParJour = 8;
    }

    public enum TypeSoignant {
        INFIRMIER, AIDE_SOIGNANT, KINESITHERAPEUTE, MEDECIN, AUXILIAIRE_VIE
    }

    public enum StatutSoignant {
        DISPONIBLE, EN_VISITE, CONGE, INDISPONIBLE
    }
}
