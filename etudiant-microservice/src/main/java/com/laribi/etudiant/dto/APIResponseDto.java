package com.laribi.etudiant.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponseDto {

    private EtudiantDto etudiantDto;
    private ClasseDto classeDto;
}
