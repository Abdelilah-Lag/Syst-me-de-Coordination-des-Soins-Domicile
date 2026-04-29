package com.soinsdomicile.visit.controller;

import com.soinsdomicile.visit.model.OptimizedRoute;
import com.soinsdomicile.visit.model.VisitRequest;
import com.soinsdomicile.visit.service.RouteOptimizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
@Tag(name = "Visit Optimization", description = "API d'optimisation des tournées de visites")
public class VisitOptimizationController {

    private final RouteOptimizationService optimizationService;

    @PostMapping("/optimize/{caregiverId}")
    @Operation(summary = "Optimiser la tournée d'un soignant pour une date donnée")
    public ResponseEntity<OptimizedRoute> optimizeRoute(
            @PathVariable Long caregiverId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody List<VisitRequest> visits) {
        OptimizedRoute route = optimizationService.optimizeRoute(caregiverId, date, visits);
        return ResponseEntity.ok(route);
    }

    @GetMapping("/distance")
    @Operation(summary = "Calculer la distance entre deux points GPS (Haversine)")
    public ResponseEntity<Double> calculateDistance(
            @RequestParam double lat1, @RequestParam double lon1,
            @RequestParam double lat2, @RequestParam double lon2) {
        double distance = optimizationService.haversineDistance(lat1, lon1, lat2, lon2);
        return ResponseEntity.ok(distance);
    }
}
