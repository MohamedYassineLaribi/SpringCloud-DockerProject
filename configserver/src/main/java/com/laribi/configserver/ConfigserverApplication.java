package com.laribi.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Classe principale du Spring Cloud Config Server.
 *
 * @EnableConfigServer active le mécanisme Config Server :
 *   - expose les endpoints /{application}/{profile} et /{application}/{profile}/{label}
 *   - lit les fichiers de configuration depuis classpath, filesystem ou Git
 *     selon le profil actif (native ou git).
 *
 * Port : 9999
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigserverApplication.class, args);
    }
}
