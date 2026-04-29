package com.soinsdomicile.tracking.service;

import com.soinsdomicile.tracking.model.VisitStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class TrackingService {

    private final Map<String, VisitStatus> visitStatusMap = new ConcurrentHashMap<>();

    public VisitStatus createVisit(Long patientId, Long caregiverId) {
        String visitId = UUID.randomUUID().toString();
        VisitStatus status = VisitStatus.builder()
                .visitId(visitId)
                .patientId(patientId)
                .caregiverId(caregiverId)
                .statut(VisitStatus.StatutVisite.PLANIFIEE)
                .lastUpdated(LocalDateTime.now())
                .build();
        visitStatusMap.put(visitId, status);
        log.info("Visite créée: {}", visitId);
        return status;
    }

    public VisitStatus updateStatus(String visitId, VisitStatus.StatutVisite statut, Double lat, Double lon, String notes) {
        VisitStatus status = visitStatusMap.get(visitId);
        if (status == null) {
            throw new RuntimeException("Visite non trouvée: " + visitId);
        }

        status.setStatut(statut);
        status.setLastUpdated(LocalDateTime.now());

        if (lat != null) status.setCaregiverLatitude(lat);
        if (lon != null) status.setCaregiverLongitude(lon);
        if (notes != null) status.setNotes(notes);

        if (statut == VisitStatus.StatutVisite.EN_COURS && status.getHeureDebutReelle() == null) {
            status.setHeureDebutReelle(LocalDateTime.now());
        }
        if (statut == VisitStatus.StatutVisite.TERMINEE && status.getHeureFinReelle() == null) {
            status.setHeureFinReelle(LocalDateTime.now());
        }

        log.info("Statut visite {} mis à jour: {}", visitId, statut);
        return status;
    }

    public VisitStatus getVisitStatus(String visitId) {
        VisitStatus status = visitStatusMap.get(visitId);
        if (status == null) {
            throw new RuntimeException("Visite non trouvée: " + visitId);
        }
        return status;
    }

    public List<VisitStatus> getAllActiveVisits() {
        return visitStatusMap.values().stream()
                .filter(v -> v.getStatut() != VisitStatus.StatutVisite.TERMINEE
                        && v.getStatut() != VisitStatus.StatutVisite.ANNULEE)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<VisitStatus> getVisitsByCaregiver(Long caregiverId) {
        return new ArrayList<>(visitStatusMap.values().stream()
                .filter(v -> caregiverId.equals(v.getCaregiverId()))
                .toList());
    }

    public VisitStatus reportIncident(String visitId, String description) {
        VisitStatus status = getVisitStatus(visitId);
        status.setStatut(VisitStatus.StatutVisite.INCIDENT);
        status.setNotes("INCIDENT: " + description);
        status.setLastUpdated(LocalDateTime.now());
        log.warn("Incident signalé pour visite {}: {}", visitId, description);
        return status;
    }
}
