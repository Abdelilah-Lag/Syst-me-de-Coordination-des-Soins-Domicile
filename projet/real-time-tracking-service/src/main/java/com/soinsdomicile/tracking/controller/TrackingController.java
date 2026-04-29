package com.soinsdomicile.tracking.controller;

import com.soinsdomicile.tracking.model.VisitStatus;
import com.soinsdomicile.tracking.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tracking")
@RequiredArgsConstructor
@Tag(name = "Tracking", description = "API de suivi en temps réel des visites")
public class TrackingController {

    private final TrackingService trackingService;

    @PostMapping("/visits")
    @Operation(summary = "Créer une nouvelle visite à suivre")
    public ResponseEntity<VisitStatus> createVisit(
            @RequestParam Long patientId,
            @RequestParam Long caregiverId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trackingService.createVisit(patientId, caregiverId));
    }

    @GetMapping("/visits/{visitId}")
    @Operation(summary = "Obtenir le statut d'une visite")
    public ResponseEntity<VisitStatus> getVisitStatus(@PathVariable String visitId) {
        return ResponseEntity.ok(trackingService.getVisitStatus(visitId));
    }

    @PutMapping("/visits/{visitId}/status")
    @Operation(summary = "Mettre à jour le statut d'une visite")
    public ResponseEntity<VisitStatus> updateStatus(
            @PathVariable String visitId,
            @RequestBody Map<String, Object> update) {
        VisitStatus.StatutVisite statut = VisitStatus.StatutVisite.valueOf(update.get("statut").toString());
        Double lat = update.containsKey("latitude") ? Double.parseDouble(update.get("latitude").toString()) : null;
        Double lon = update.containsKey("longitude") ? Double.parseDouble(update.get("longitude").toString()) : null;
        String notes = update.containsKey("notes") ? update.get("notes").toString() : null;
        return ResponseEntity.ok(trackingService.updateStatus(visitId, statut, lat, lon, notes));
    }

    @GetMapping("/visits/active")
    @Operation(summary = "Lister toutes les visites actives")
    public ResponseEntity<List<VisitStatus>> getAllActiveVisits() {
        return ResponseEntity.ok(trackingService.getAllActiveVisits());
    }

    @GetMapping("/caregivers/{caregiverId}/visits")
    @Operation(summary = "Lister les visites d'un soignant")
    public ResponseEntity<List<VisitStatus>> getVisitsByCaregiver(@PathVariable Long caregiverId) {
        return ResponseEntity.ok(trackingService.getVisitsByCaregiver(caregiverId));
    }

    @PostMapping("/visits/{visitId}/incident")
    @Operation(summary = "Signaler un incident lors d'une visite")
    public ResponseEntity<VisitStatus> reportIncident(
            @PathVariable String visitId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(trackingService.reportIncident(visitId, body.get("description")));
    }
}
