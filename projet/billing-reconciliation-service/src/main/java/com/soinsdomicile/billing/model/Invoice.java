package com.soinsdomicile.billing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroFacture;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long caregiverId;

    private String visitId;

    @Column(nullable = false)
    private LocalDate dateVisite;

    private Integer dureeMinutes;
    private String typePrestation;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montantBrut;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantPrisEnCharge;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantRestantDu;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut;

    private String organismePayeur;
    private String numeroPriseEnCharge;

    private LocalDateTime dateCreation;
    private LocalDateTime datePaiement;

    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();
        if (this.statut == null) this.statut = StatutFacture.EN_ATTENTE;
    }

    public enum StatutFacture {
        EN_ATTENTE, SOUMISE, VALIDEE, PAYEE, REJETEE, ANNULEE
    }
}
