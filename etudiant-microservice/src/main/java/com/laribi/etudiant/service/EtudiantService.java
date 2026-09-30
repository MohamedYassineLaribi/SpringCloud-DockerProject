package com.laribi.etudiant.service;

import com.laribi.etudiant.dto.APIResponseDto;

public interface EtudiantService {

    APIResponseDto getEtudiantById(Long id);
}
