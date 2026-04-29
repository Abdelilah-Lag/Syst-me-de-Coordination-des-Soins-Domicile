package com.soinsdomicile.caregiver.service;

import com.soinsdomicile.caregiver.dto.CaregiverDTO;
import com.soinsdomicile.caregiver.model.Caregiver;
import com.soinsdomicile.caregiver.repository.CaregiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;

    public List<CaregiverDTO> getAllCaregivers() {
        return caregiverRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CaregiverDTO getCaregiverById(Long id) {
        return toDTO(caregiverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soignant non trouvé: " + id)));
    }

    public CaregiverDTO createCaregiver(CaregiverDTO dto) {
        Caregiver c = Caregiver.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .typeSoignant(dto.getTypeSoignant())
                .telephone(dto.getTelephone())
                .email(dto.getEmail())
                .adresseBase(dto.getAdresseBase())
                .latitudeBase(dto.getLatitudeBase())
                .longitudeBase(dto.getLongitudeBase())
                .competences(dto.getCompetences())
                .capaciteVisitesParJour(dto.getCapaciteVisitesParJour())
                .build();
        return toDTO(caregiverRepository.save(c));
    }

    public CaregiverDTO updateCaregiver(Long id, CaregiverDTO dto) {
        Caregiver existing = caregiverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soignant non trouvé: " + id));
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setTypeSoignant(dto.getTypeSoignant());
        existing.setTelephone(dto.getTelephone());
        existing.setEmail(dto.getEmail());
        existing.setAdresseBase(dto.getAdresseBase());
        existing.setLatitudeBase(dto.getLatitudeBase());
        existing.setLongitudeBase(dto.getLongitudeBase());
        existing.setCompetences(dto.getCompetences());
        existing.setCapaciteVisitesParJour(dto.getCapaciteVisitesParJour());
        if (dto.getStatut() != null) existing.setStatut(dto.getStatut());
        return toDTO(caregiverRepository.save(existing));
    }

    public void deleteCaregiver(Long id) {
        caregiverRepository.deleteById(id);
    }

    public List<CaregiverDTO> getAvailableCaregivers() {
        return caregiverRepository.findByStatut(Caregiver.StatutSoignant.DISPONIBLE)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CaregiverDTO toDTO(Caregiver c) {
        CaregiverDTO dto = new CaregiverDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        dto.setPrenom(c.getPrenom());
        dto.setTypeSoignant(c.getTypeSoignant());
        dto.setTelephone(c.getTelephone());
        dto.setEmail(c.getEmail());
        dto.setAdresseBase(c.getAdresseBase());
        dto.setLatitudeBase(c.getLatitudeBase());
        dto.setLongitudeBase(c.getLongitudeBase());
        dto.setCompetences(c.getCompetences());
        dto.setCapaciteVisitesParJour(c.getCapaciteVisitesParJour());
        dto.setStatut(c.getStatut());
        return dto;
    }
}
