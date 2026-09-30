package com.laribi.classe.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Mappe les propriétés commençant par "author" dans la configuration (YAML ou autre)
 * vers les champs de cette classe.
 *
 * Contrairement à @Value qui injecte une seule valeur,
 * @ConfigurationProperties groupe logiquement plusieurs propriétés liées.
 */
@Data
@Component
@ConfigurationProperties(prefix = "author")
public class Configuration {

    private String name;
    private String email;
}
