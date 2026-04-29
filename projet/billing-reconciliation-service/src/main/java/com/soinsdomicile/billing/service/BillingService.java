package com.soinsdomicile.billing.service;

import com.soinsdomicile.billing.dto.InvoiceDTO;
import com.soinsdomicile.billing.model.Invoice;
import com.soinsdomicile.billing.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final InvoiceRepository invoiceRepository;

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public InvoiceDTO getInvoiceById(Long id) {
        return toDTO(invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée: " + id)));
    }

    public InvoiceDTO createInvoice(InvoiceDTO dto) {
        Invoice invoice = Invoice.builder()
                .numeroFacture("FACT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .patientId(dto.getPatientId())
                .caregiverId(dto.getCaregiverId())
                .visitId(dto.getVisitId())
                .dateVisite(dto.getDateVisite())
                .dureeMinutes(dto.getDureeMinutes())
                .typePrestation(dto.getTypePrestation())
                .montantBrut(dto.getMontantBrut())
                .montantPrisEnCharge(dto.getMontantPrisEnCharge() != null ? dto.getMontantPrisEnCharge() : BigDecimal.ZERO)
                .montantRestantDu(computeRestant(dto))
                .organismePayeur(dto.getOrganismePayeur())
                .numeroPriseEnCharge(dto.getNumeroPriseEnCharge())
                .build();
        return toDTO(invoiceRepository.save(invoice));
    }

    public InvoiceDTO updateStatut(Long id, Invoice.StatutFacture statut) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée: " + id));
        invoice.setStatut(statut);
        if (statut == Invoice.StatutFacture.PAYEE) {
            invoice.setDatePaiement(LocalDateTime.now());
        }
        return toDTO(invoiceRepository.save(invoice));
    }

    public List<InvoiceDTO> getInvoicesByPatient(Long patientId) {
        return invoiceRepository.findByPatientId(patientId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByCaregiver(Long caregiverId) {
        return invoiceRepository.findByCaregiverId(caregiverId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private BigDecimal computeRestant(InvoiceDTO dto) {
        if (dto.getMontantBrut() == null) return BigDecimal.ZERO;
        BigDecimal pec = dto.getMontantPrisEnCharge() != null ? dto.getMontantPrisEnCharge() : BigDecimal.ZERO;
        return dto.getMontantBrut().subtract(pec);
    }

    private InvoiceDTO toDTO(Invoice i) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(i.getId());
        dto.setNumeroFacture(i.getNumeroFacture());
        dto.setPatientId(i.getPatientId());
        dto.setCaregiverId(i.getCaregiverId());
        dto.setVisitId(i.getVisitId());
        dto.setDateVisite(i.getDateVisite());
        dto.setDureeMinutes(i.getDureeMinutes());
        dto.setTypePrestation(i.getTypePrestation());
        dto.setMontantBrut(i.getMontantBrut());
        dto.setMontantPrisEnCharge(i.getMontantPrisEnCharge());
        dto.setMontantRestantDu(i.getMontantRestantDu());
        dto.setStatut(i.getStatut());
        dto.setOrganismePayeur(i.getOrganismePayeur());
        dto.setNumeroPriseEnCharge(i.getNumeroPriseEnCharge());
        return dto;
    }
}
