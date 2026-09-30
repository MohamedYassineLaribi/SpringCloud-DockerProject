package com.laribi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de la Spring Cloud API Gateway.
 *
 * La Gateway :
 *   - s'enregistre dans Eureka (grâce à @SpringBootApplication + eureka-client)
 *   - découvre automatiquement CLASSE et ETUDIANT via Eureka
 *   - route les requêtes vers les microservices (lb://CLASSE, lb://ETUDIANT)
 *
 * Port : 8888
 */
@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
