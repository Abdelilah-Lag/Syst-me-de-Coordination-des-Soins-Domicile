package com.soinsdomicile.billing.controller;

import com.soinsdomicile.billing.dto.InvoiceDTO;
import com.soinsdomicile.billing.model.Invoice;
import com.soinsdomicile.billing.service.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
@Tag(name = "Billing", description = "API de facturation et rapprochement")
public class BillingController {

    private final BillingService billingService;

    @GetMapping
    @Operation(summary = "Lister toutes les factures")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        return ResponseEntity.ok(billingService.getAllInvoices());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une facture par ID")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getInvoiceById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle facture")
    public ResponseEntity<InvoiceDTO> createInvoice(@Valid @RequestBody InvoiceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createInvoice(dto));
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Mettre à jour le statut d'une facture")
    public ResponseEntity<InvoiceDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam Invoice.StatutFacture statut) {
        return ResponseEntity.ok(billingService.updateStatut(id, statut));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Factures d'un patient")
    public ResponseEntity<List<InvoiceDTO>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(billingService.getInvoicesByPatient(patientId));
    }

    @GetMapping("/caregiver/{caregiverId}")
    @Operation(summary = "Factures d'un soignant")
    public ResponseEntity<List<InvoiceDTO>> getByCaregiver(@PathVariable Long caregiverId) {
        return ResponseEntity.ok(billingService.getInvoicesByCaregiver(caregiverId));
    }
}
