package com.laribi.classe.restControllers;

import com.laribi.classe.config.Configuration;
import com.laribi.classe.dto.ClasseDto;
import com.laribi.classe.service.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope // Indispensable pour que @Value soit mis à jour lors de /actuator/refresh
@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;
    private final Configuration configuration; // Injecté via Constructor (Lombok)

    // @Value injecte une valeur scalaire depuis l'environnement Spring (Config Server, YAML, etc.)
    @Value("${build.version:INCONNU}") // "INCONNU" est une valeur par défaut si la propriété n'existe pas
    private String buildVersion;

    /**
     * Récupère une classe par son code.
     * Exemple : GET http://localhost:8080/api/classes/DI
     */
    @GetMapping("{code}")
    public ResponseEntity<ClasseDto> getClasseByCode(@PathVariable String code) {
        ClasseDto classeDto = classeService.getClasseByCode(code);
        return ResponseEntity.ok(classeDto);
    }

    /**
     * Exemple d'utilisation de @Value
     * Test : GET http://localhost:8080/api/classes/version
     */
    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    /**
     * Exemple d'utilisation de @ConfigurationProperties
     * Test : GET http://localhost:8080/api/classes/author
     */
    @GetMapping("/author")
    public ResponseEntity<String> retrieveAuthorInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(configuration.getName() + " " + configuration.getEmail());
    }
}

