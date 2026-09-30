package com.laribi.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Java des routes de la Gateway.
 *
 * IMPORTANT : Cette classe est fournie pour illustrer la PARTIE 8 (RouteLocator).
 * Pour la PARTIE 10 (routes YAML), commentez le @Configuration ou supprimez cette
 * classe afin d'éviter les conflits avec les routes définies dans application.yml.
 *
 * Fonctionnement de lb://
 * -----------------------
 * "lb" = Load Balancer
 * "lb://CLASSE"   → Gateway interroge Eureka pour obtenir les instances de CLASSE
 *                   puis Spring Cloud LoadBalancer choisit une instance (round-robin).
 * "lb://ETUDIANT" → Idem pour le service ETUDIANT.
 *
 * Exemple de flux :
 *   GET http://localhost:8888/api/classes/DI
 *      → Gateway → Eureka (cherche CLASSE) → LoadBalancer → http://localhost:8080/api/classes/DI
 */
// @Configuration  ← Décommentez cette ligne pour activer le routage Java (PARTIE 8)
//                   Commentez-la pour utiliser le routage YAML (PARTIE 10)
@Configuration
public class GatewayConfig {

    /**
     * Définit les routes de manière programmatique.
     *
     * Route "classe-service" :
     *   - prédicat : toutes les requêtes dont le chemin commence par /api/classes/
     *   - destination : lb://CLASSE (Eureka + LoadBalancer)
     *
     * Route "etudiant-service" :
     *   - prédicat : toutes les requêtes dont le chemin commence par /api/etudiants/
     *   - destination : lb://ETUDIANT (Eureka + LoadBalancer)
     *
     * NOTA : Quand le routage automatique (discovery.locator) est activé,
     * ces routes s'y ajoutent. Pour n'utiliser QUE ces routes, désactiver
     * discovery.locator dans application.yml.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Route vers classe-microservice
                .route("classe-service", r -> r
                        .path("/api/classes/**")          // si le chemin commence par /api/classes/
                        .uri("lb://CLASSE"))               // → envoyer vers CLASSE via LoadBalancer

                // Route vers etudiant-microservice
                .route("etudiant-service", r -> r
                        .path("/api/etudiants/**")         // si le chemin commence par /api/etudiants/
                        .uri("lb://ETUDIANT"))             // → envoyer vers ETUDIANT via LoadBalancer

                .build();
    }
}
