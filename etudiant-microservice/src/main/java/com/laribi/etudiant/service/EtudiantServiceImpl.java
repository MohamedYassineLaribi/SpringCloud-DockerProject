package com.laribi.etudiant.service;

import com.laribi.etudiant.dto.APIResponseDto;
import com.laribi.etudiant.dto.ClasseDto;
import com.laribi.etudiant.dto.EtudiantDto;
import com.laribi.etudiant.entities.Etudiant;
import com.laribi.etudiant.repos.EtudiantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private EtudiantRepository etudiantRepository;
    private APIClient apiClient;

    @Override
    public APIResponseDto getEtudiantById(Long id) {

        // 1. Chercher l'étudiant dans H2
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable pour l'id : " + id));

        // 2. Appeler classe-microservice via OpenFeign
        ClasseDto classeDto = apiClient.getClasseByCode(etudiant.getClasseCode());

        // 3. Construire EtudiantDto avec classeName récupéré
        EtudiantDto etudiantDto = EtudiantDto.builder()
                .id(etudiant.getId())
                .firstName(etudiant.getFirstName())
                .lastName(etudiant.getLastName())
                .classeCode(etudiant.getClasseCode())
                .classeName(classeDto.getClasseName())
                .build();

        // 4. Retourner APIResponseDto
        return APIResponseDto.builder()
                .etudiantDto(etudiantDto)
                .classeDto(classeDto)
                .build();
    }
}
