# Projet 6 - Système de Coordination des Soins à Domicile

**Auteurs :** Costo Aya & Lagribi Abdelilah  
**Filière :** INDIA (2ème année – S4) | ENSAM Rabat  
**A.U. :** 2025/2026 | Pr. A. El Qadi

---

## Démarrage rapide

```bash
docker-compose up --build
```

## Services & Ports

| Service                        | Port |
|-------------------------------|------|
| Eureka Server (Discovery)      | 8761 |
| API Gateway                    | 8080 |
| Patient Home Service           | 8081 |
| Caregiver Scheduling Service   | 8082 |
| Visit Optimization Service     | 8083 |
| Real-Time Tracking Service     | 8084 |
| Billing Reconciliation Service | 8085 |

## Swagger UI (documentation API)

- http://localhost:8081/swagger-ui.html
- http://localhost:8082/swagger-ui.html
- http://localhost:8083/swagger-ui.html
- http://localhost:8084/swagger-ui.html
- http://localhost:8085/swagger-ui.html

## Eureka Dashboard

- http://localhost:8761

## Collection Postman

Importer le fichier `postman_collection.json` dans Postman pour tester toutes les APIs.
