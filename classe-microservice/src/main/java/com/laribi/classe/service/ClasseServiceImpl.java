package com.laribi.classe.service;

import com.laribi.classe.dto.ClasseDto;
import com.laribi.classe.entities.Classe;
import com.laribi.classe.repos.ClasseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;

    @Override
    public ClasseDto getClasseByCode(String code) {
        Classe classe = classeRepository.findByClasseCode(code);

        if (classe == null) {
            throw new RuntimeException("Classe introuvable pour le code : " + code);
        }

        return ClasseDto.builder()
                .id(classe.getId())
                .classeName(classe.getClasseName())
                .classeCode(classe.getClasseCode())
                .build();
    }
}
