package com.soinsdomicile.billing.dto;

import com.soinsdomicile.billing.model.Invoice;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDTO {
    private Long id;
    private String numeroFacture;

    @NotNull
    private Long patientId;

    @NotNull
    private Long caregiverId;

    private String visitId;

    @NotNull
    private LocalDate dateVisite;

    private Integer dureeMinutes;
    private String typePrestation;

    @NotNull
    private BigDecimal montantBrut;

    private BigDecimal montantPrisEnCharge;
    private BigDecimal montantRestantDu;
    private Invoice.StatutFacture statut;
    private String organismePayeur;
    private String numeroPriseEnCharge;
}
