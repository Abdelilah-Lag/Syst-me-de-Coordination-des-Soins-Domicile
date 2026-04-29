package com.soinsdomicile.visit.service;

import com.soinsdomicile.visit.model.OptimizedRoute;
import com.soinsdomicile.visit.model.VisitRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class RouteOptimizationService {

    private static final double VITESSE_MOYENNE_KMH = 30.0;

    /**
     * Algorithme d'optimisation de tournées basé sur le plus proche voisin (Nearest Neighbor Heuristic).
     * Stratégie greedy: à chaque étape, choisir la visite non encore planifiée la plus proche.
     */
    public OptimizedRoute optimizeRoute(Long caregiverId, LocalDate date, List<VisitRequest> visits) {
        if (visits == null || visits.isEmpty()) {
            return OptimizedRoute.builder()
                    .caregiverId(caregiverId)
                    .date(date)
                    .stops(new ArrayList<>())
                    .distanceTotaleKm(0.0)
                    .dureeEstimeeMinutes(0)
                    .nombreVisites(0)
                    .build();
        }

        List<VisitRequest> remaining = new ArrayList<>(visits);
        List<OptimizedRoute.VisitStop> stops = new ArrayList<>();

        double currentLat = 33.5731;
        double currentLon = -7.5898;
        double totalDistance = 0.0;
        int totalMinutes = 0;
        LocalTime currentTime = LocalTime.of(8, 0);
        int ordre = 1;

        while (!remaining.isEmpty()) {
            VisitRequest nearest = findNearest(currentLat, currentLon, remaining);
            remaining.remove(nearest);

            double dist = haversineDistance(currentLat, currentLon,
                    nearest.getPatientLatitude() != null ? nearest.getPatientLatitude() : 33.5 + Math.random() * 0.5,
                    nearest.getPatientLongitude() != null ? nearest.getPatientLongitude() : -7.5 + Math.random() * 0.5);

            int travelMinutes = (int) (dist / VITESSE_MOYENNE_KMH * 60);
            currentTime = currentTime.plusMinutes(travelMinutes);

            int duree = nearest.getDureeMinutes() != null ? nearest.getDureeMinutes() : 30;

            OptimizedRoute.VisitStop stop = OptimizedRoute.VisitStop.builder()
                    .ordre(ordre++)
                    .patientId(nearest.getPatientId())
                    .patientNom("Patient #" + nearest.getPatientId())
                    .adresse("Adresse patient " + nearest.getPatientId())
                    .latitude(nearest.getPatientLatitude())
                    .longitude(nearest.getPatientLongitude())
                    .heureArriveeEstimee(currentTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                    .dureeMinutes(duree)
                    .distanceDepuisPrecedentKm(Math.round(dist * 100.0) / 100.0)
                    .build();

            stops.add(stop);
            totalDistance += dist;
            totalMinutes += travelMinutes + duree;

            currentLat = nearest.getPatientLatitude() != null ? nearest.getPatientLatitude() : 33.5;
            currentLon = nearest.getPatientLongitude() != null ? nearest.getPatientLongitude() : -7.5;
            currentTime = currentTime.plusMinutes(duree);
        }

        return OptimizedRoute.builder()
                .caregiverId(caregiverId)
                .date(date)
                .stops(stops)
                .distanceTotaleKm(Math.round(totalDistance * 100.0) / 100.0)
                .dureeEstimeeMinutes(totalMinutes)
                .nombreVisites(stops.size())
                .build();
    }

    private VisitRequest findNearest(double lat, double lon, List<VisitRequest> candidates) {
        VisitRequest nearest = null;
        double minDist = Double.MAX_VALUE;
        for (VisitRequest v : candidates) {
            double pLat = v.getPatientLatitude() != null ? v.getPatientLatitude() : 33.5 + Math.random() * 0.5;
            double pLon = v.getPatientLongitude() != null ? v.getPatientLongitude() : -7.5 + Math.random() * 0.5;
            double d = haversineDistance(lat, lon, pLat, pLon);
            if (d < minDist) {
                minDist = d;
                nearest = v;
            }
        }
        return nearest;
    }

    /**
     * Calcul de distance entre deux points GPS (formule de Haversine)
     */
    public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
