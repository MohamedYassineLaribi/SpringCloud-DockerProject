package com.laribi.classe.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClasseDto {

    private Long id;
    private String classeName;
    private String classeCode;
}
