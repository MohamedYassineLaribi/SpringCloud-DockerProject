package com.laribi.etudiant.service;

import com.laribi.etudiant.dto.ClasseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "classe")
public interface APIClient {

    @GetMapping("/api/classes/{classeCode}")
    ClasseDto getClasseByCode(@PathVariable("classeCode") String classeCode);
}
