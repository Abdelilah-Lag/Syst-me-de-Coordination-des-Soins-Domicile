package com.soinsdomicile.billing.repository;

import com.soinsdomicile.billing.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByPatientId(Long patientId);
    List<Invoice> findByCaregiverId(Long caregiverId);
    List<Invoice> findByStatut(Invoice.StatutFacture statut);
    Optional<Invoice> findByNumeroFacture(String numeroFacture);
}
