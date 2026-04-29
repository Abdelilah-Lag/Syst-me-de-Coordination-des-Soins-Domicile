package com.soinsdomicile.patient.repository;

import com.soinsdomicile.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByStatut(Patient.StatutPatient statut);

    List<Patient> findByNiveauDependance(Patient.NiveauDependance niveau);

    List<Patient> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
