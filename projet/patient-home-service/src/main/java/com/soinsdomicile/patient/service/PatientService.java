package com.soinsdomicile.patient.service;

import com.soinsdomicile.patient.dto.PatientDTO;
import com.soinsdomicile.patient.model.Patient;
import com.soinsdomicile.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'id: " + id));
        return toDTO(patient);
    }

    public PatientDTO createPatient(PatientDTO dto) {
        Patient patient = toEntity(dto);
        return toDTO(patientRepository.save(patient));
    }

    public PatientDTO updatePatient(Long id, PatientDTO dto) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'id: " + id));
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setDateNaissance(dto.getDateNaissance());
        existing.setTelephone(dto.getTelephone());
        existing.setEmail(dto.getEmail());
        existing.setAdresse(dto.getAdresse());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setNiveauDependance(dto.getNiveauDependance());
        existing.setPathologies(dto.getPathologies());
        existing.setBesoinsSpecifiques(dto.getBesoinsSpecifiques());
        existing.setContraintes(dto.getContraintes());
        existing.setNomAidant(dto.getNomAidant());
        existing.setTelephoneAidant(dto.getTelephoneAidant());
        if (dto.getStatut() != null) existing.setStatut(dto.getStatut());
        return toDTO(patientRepository.save(existing));
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<PatientDTO> searchPatients(String query) {
        return patientRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PatientDTO toDTO(Patient p) {
        PatientDTO dto = new PatientDTO();
        dto.setId(p.getId());
        dto.setNom(p.getNom());
        dto.setPrenom(p.getPrenom());
        dto.setDateNaissance(p.getDateNaissance());
        dto.setTelephone(p.getTelephone());
        dto.setEmail(p.getEmail());
        dto.setAdresse(p.getAdresse());
        dto.setLatitude(p.getLatitude());
        dto.setLongitude(p.getLongitude());
        dto.setNiveauDependance(p.getNiveauDependance());
        dto.setPathologies(p.getPathologies());
        dto.setBesoinsSpecifiques(p.getBesoinsSpecifiques());
        dto.setContraintes(p.getContraintes());
        dto.setNomAidant(p.getNomAidant());
        dto.setTelephoneAidant(p.getTelephoneAidant());
        dto.setStatut(p.getStatut());
        return dto;
    }

    private Patient toEntity(PatientDTO dto) {
        return Patient.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .telephone(dto.getTelephone())
                .email(dto.getEmail())
                .adresse(dto.getAdresse())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .niveauDependance(dto.getNiveauDependance())
                .pathologies(dto.getPathologies())
                .besoinsSpecifiques(dto.getBesoinsSpecifiques())
                .contraintes(dto.getContraintes())
                .nomAidant(dto.getNomAidant())
                .telephoneAidant(dto.getTelephoneAidant())
                .build();
    }
}
