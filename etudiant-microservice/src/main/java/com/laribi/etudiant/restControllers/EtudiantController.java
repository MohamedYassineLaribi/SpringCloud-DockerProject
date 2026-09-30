package com.laribi.etudiant.restControllers;

import com.laribi.etudiant.dto.APIResponseDto;
import com.laribi.etudiant.service.EtudiantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/etudiants")
@AllArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;

    /**
     * Récupère un étudiant par son id, avec les informations de sa classe.
     * Exemple : GET http://localhost:8081/api/etudiants/1
     */
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getEtudiantById(@PathVariable Long id) {
        APIResponseDto response = etudiantService.getEtudiantById(id);
        return ResponseEntity.ok(response);
    }
}
