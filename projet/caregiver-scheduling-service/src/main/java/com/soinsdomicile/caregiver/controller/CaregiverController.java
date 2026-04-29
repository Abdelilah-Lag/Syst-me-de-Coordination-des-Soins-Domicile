package com.soinsdomicile.caregiver.controller;

import com.soinsdomicile.caregiver.dto.CaregiverDTO;
import com.soinsdomicile.caregiver.service.CaregiverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caregivers")
@RequiredArgsConstructor
@Tag(name = "Caregiver", description = "API de gestion des soignants et plannings")
public class CaregiverController {

    private final CaregiverService caregiverService;

    @GetMapping
    @Operation(summary = "Lister tous les soignants")
    public ResponseEntity<List<CaregiverDTO>> getAllCaregivers() {
        return ResponseEntity.ok(caregiverService.getAllCaregivers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un soignant par ID")
    public ResponseEntity<CaregiverDTO> getCaregiverById(@PathVariable Long id) {
        return ResponseEntity.ok(caregiverService.getCaregiverById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un soignant")
    public ResponseEntity<CaregiverDTO> createCaregiver(@Valid @RequestBody CaregiverDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(caregiverService.createCaregiver(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un soignant")
    public ResponseEntity<CaregiverDTO> updateCaregiver(@PathVariable Long id, @Valid @RequestBody CaregiverDTO dto) {
        return ResponseEntity.ok(caregiverService.updateCaregiver(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un soignant")
    public ResponseEntity<Void> deleteCaregiver(@PathVariable Long id) {
        caregiverService.deleteCaregiver(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    @Operation(summary = "Lister les soignants disponibles")
    public ResponseEntity<List<CaregiverDTO>> getAvailableCaregivers() {
        return ResponseEntity.ok(caregiverService.getAvailableCaregivers());
    }
}
