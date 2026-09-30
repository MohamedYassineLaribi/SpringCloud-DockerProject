# Projet Spring Cloud - Architecture Microservices

## 📋 Vue d'ensemble

Ce projet implémente une architecture microservices complète avec Spring Cloud, comprenant:

- **2 Microservices métier:** Classe et Etudiant
- **Service Registry:** Eureka Server pour la découverte de services
- **Config Server:** Centralisation de la configuration
- **API Gateway:** Point d'entrée unique pour tous les services
- **Communication inter-services:** OpenFeign
- **Load Balancing:** Spring Cloud LoadBalancer

---

## 🏗️ Architecture

```
┌─────────────────┐
│   Utilisateur   │
└────────┬────────┘
         │
         v
┌─────────────────┐
│  API Gateway    │ (Port 8888)
│   (Routage +    │
│  Load Balancer) │
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
    v         v
┌────────┐  ┌──────────┐
│ Classe │  │ Etudiant │
│  (8080)│  │  (8081)  │
└───┬────┘  └────┬─────┘
    │            │
    │            └──────> Appel Feign
    │                     vers Classe
    │
    v
┌─────────────────┐
│ Eureka Server   │ (Port 8761)
│ (Service        │
│  Discovery)     │
└─────────────────┘

┌─────────────────┐
│ Config Server   │ (Port 9999)
│ (Configuration  │
│  Centralisée)   │
└─────────────────┘
```

---

## 🚀 Démarrage Rapide

### Option 1: Script Automatique (Windows)

```bash
start-all.bat
```

Ce script démarre automatiquement tous les services dans le bon ordre avec des délais appropriés.

### Option 2: Démarrage Manuel

Démarrer les services **dans cet ordre précis:**

```bash
# 1. Config Server (port 9999)
cd configserver
mvn spring-boot:run

# 2. Eureka Server (port 8761)
cd eurekaserver
mvn spring-boot:run

# 3. Classe Microservice (port 8080)
cd classe-microservice
mvn spring-boot:run

# 4. Etudiant Microservice (port 8081)
cd etudiant-microservice
mvn spring-boot:run

# 5. Gateway Server (port 8888)
cd gatewayserver
mvn spring-boot:run
```

**Temps d'attente recommandé entre chaque service:** 15-20 secondes

---

## 🔍 Vérification

### 1. Eureka Dashboard
Ouvrir http://localhost:8761 pour vérifier que tous les services sont enregistrés:
- CLASSE
- ETUDIANT
- GATEWAYSERVER

### 2. Config Server
Vérifier la configuration:
```
http://localhost:9999/classe/prod
http://localhost:9999/etudiant/default
```

### 3. Gateway Routes
Voir les routes configurées:
```
http://localhost:8888/actuator/gateway/routes
```

---

## 📡 Endpoints de Test

### Via Gateway (Recommandé)

```bash
# Classe Microservice
GET http://localhost:8888/classe/api/classes/DI
GET http://localhost:8888/classe/api/classes/SI
GET http://localhost:8888/classe/api/classes/version
GET http://localhost:8888/classe/api/classes/author

# Etudiant Microservice (avec appel inter-service)
GET http://localhost:8888/etudiant/api/etudiants/1
GET http://localhost:8888/etudiant/api/etudiants/2
```

### Accès Direct

```bash
# Classe Microservice
GET http://localhost:8080/api/classes/DI
GET http://localhost:8080/api/classes/SI

# Etudiant Microservice
GET http://localhost:8081/api/etudiants/1
GET http://localhost:8081/api/etudiants/2
```

---

## 📦 Microservices Détaillés

### 1. Classe Microservice (Port 8080)

**Responsabilité:** Gestion des classes

**Données initiales:**
- DI: Développement Informatique
- SI: Systèmes Informatiques

**Endpoints:**
- `GET /api/classes/{code}` - Récupérer une classe par son code
- `GET /api/classes/version` - Version de build (depuis Config Server)
- `GET /api/classes/author` - Informations auteur (depuis Config Server)

**Technologies:**
- Spring Web
- Spring Data JPA
- H2 Database (in-memory)
- Spring Cloud Config Client
- Eureka Client

---

### 2. Etudiant Microservice (Port 8081)

**Responsabilité:** Gestion des étudiants avec communication vers Classe

**Données initiales:**
- Etudiant #1: Mohamed Laribi (classe DI)
- Etudiant #2: Ahmed Ben Ali (classe SI)

**Endpoints:**
- `GET /api/etudiants/{id}` - Récupérer un étudiant avec ses informations de classe

**Communication inter-services:**
- Utilise OpenFeign pour appeler le Classe Microservice
- Load balancing automatique via Eureka

**Technologies:**
- Spring Web
- Spring Data JPA
- H2 Database (in-memory)
- OpenFeign
- Spring Cloud LoadBalancer
- Eureka Client

---

### 3. Eureka Server (Port 8761)

**Responsabilité:** Service Registry et Discovery

**Fonctionnalités:**
- Enregistrement automatique des microservices
- Health checks
- Load balancing côté client
- Détection des instances multiples

**Dashboard:** http://localhost:8761

---

### 4. Config Server (Port 9999)

**Responsabilité:** Configuration centralisée

**Configurations disponibles:**
- `classe.yml` - Configuration par défaut pour Classe
- `classe-prod.yml` - Configuration production pour Classe
- `classe-qa.yml` - Configuration QA pour Classe
- `etudiant.yml` - Configuration par défaut pour Etudiant
- `etudiant-prod.yml` - Configuration production pour Etudiant
- `etudiant-qa.yml` - Configuration QA pour Etudiant

**Mode:** Native (lecture depuis classpath)

**Endpoints de test:**
```
GET http://localhost:9999/{application}/{profile}
GET http://localhost:9999/classe/default
GET http://localhost:9999/classe/prod
```

---

### 5. Gateway Server (Port 8888)

**Responsabilité:** Point d'entrée API unique

**Fonctionnalités:**
- Routage automatique vers les microservices enregistrés dans Eureka
- Load balancing
- Transformation d'URL (CLASSE → /classe)
- Monitoring via Actuator

**Patterns de routage:**
```
http://localhost:8888/classe/*  → CLASSE microservice
http://localhost:8888/etudiant/* → ETUDIANT microservice
```

---

## 🛠️ Technologies Utilisées

### Framework & Versions
- **Spring Boot:** 3.3.0
- **Spring Cloud:** 2023.0.3
- **Java:** 21

### Dépendances Spring Cloud
- Spring Cloud Config (Client & Server)
- Spring Cloud Netflix Eureka (Client & Server)
- Spring Cloud Gateway
- Spring Cloud OpenFeign
- Spring Cloud LoadBalancer

### Outils & Bibliothèques
- Lombok (génération de code)
- H2 Database (base de données en mémoire)
- Spring Boot Actuator (monitoring)
- Spring Data JPA
- WebFlux (pour Gateway)

---

## 🐛 Résolution des Problèmes

### Les services ne se trouvent pas entre eux
- Vérifier que Eureka Server est démarré
- Attendre 30 secondes après le démarrage pour la propagation
- Vérifier le dashboard Eureka (http://localhost:8761)

### Erreurs Lombok dans l'IDE
- Ces erreurs n'empêchent pas Maven de compiler
- Solution: `mvn clean install` puis recharger le projet
- Installer le plugin Lombok dans l'IDE
- Activer "Enable annotation processing" dans les paramètres de l'IDE

### Erreur "Connection refused" lors du démarrage
- Les services démarrent dans le désordre
- Respecter l'ordre: Config → Eureka → Microservices → Gateway

### Gateway ne route pas correctement
- Vérifier que les microservices sont enregistrés dans Eureka
- Consulter http://localhost:8888/actuator/gateway/routes

---

## 📝 Configuration des Profils

### Classe Microservice

Le profil actif est configuré dans `application.yml`:
```yaml
spring:
  profiles:
    active: prod  # default, prod, ou qa
```

- **default:** Configuration de développement
- **prod:** Configuration production (author: "Mohamed Yassine Laribi [PROD]")
- **qa:** Configuration tests

### Refresh de Configuration

Pour rafraîchir la configuration sans redémarrer:
```bash
POST http://localhost:8080/actuator/refresh
```

---

## 📊 Monitoring

### Actuator Endpoints

**Classe Microservice (8080):**
```
GET http://localhost:8080/actuator
GET http://localhost:8080/actuator/health
GET http://localhost:8080/actuator/refresh
```

**Gateway (8888):**
```
GET http://localhost:8888/actuator/gateway/routes
GET http://localhost:8888/actuator/health
```

---

## 🔒 Consoles H2

### Classe Microservice
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:classedb
Username: (vide)
Password: (vide)
```

### Etudiant Microservice
```
URL: http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:etudiantsdb
Username: (vide)
Password: (vide)
```

---

## 📚 Ressources Additionnelles

- [Documentation Spring Cloud](https://spring.io/projects/spring-cloud)
- [Spring Cloud Config](https://spring.io/projects/spring-cloud-config)
- [Netflix Eureka](https://github.com/Netflix/eureka)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
- [OpenFeign](https://github.com/OpenFeign/feign)

---

## 📄 Fichiers Importants

- `CORRECTIONS_EFFECTUEES.md` - Détails des corrections appliquées
- `start-all.bat` - Script de démarrage automatique (Windows)
- `config-repo/` - Fichiers de configuration centralisée
- `*/pom.xml` - Configuration Maven de chaque projet

---

## 👥 Auteur

Mohamed Yassine Laribi

---

## 📅 Version

**Version du projet:** 1.0.0-SNAPSHOT  
**Date de dernière mise à jour:** 2026-09-30  
**Statut:** ✅ Projet fonctionnel et testé
# SpringCloud-DockerProject
