package com.laribi.etudiant.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtudiantDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String classeCode;
    private String classeName;
}
