package com.soinsdomicile.caregiver.repository;

import com.soinsdomicile.caregiver.model.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    List<Caregiver> findByStatut(Caregiver.StatutSoignant statut);
    List<Caregiver> findByTypeSoignant(Caregiver.TypeSoignant type);
}
