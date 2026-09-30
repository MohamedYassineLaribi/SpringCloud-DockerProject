package com.laribi.classe;

import com.laribi.classe.entities.Classe;
import com.laribi.classe.repos.ClasseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClasseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClasseApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(ClasseRepository classeRepository) {
        return args -> {
            classeRepository.save(Classe.builder()
                    .classeName("Développement Informatique")
                    .classeCode("DI")
                    .build());

            classeRepository.save(Classe.builder()
                    .classeName("Systèmes Informatiques")
                    .classeCode("SI")
                    .build());

            System.out.println("=== Classes chargées avec succès ===");
            classeRepository.findAll().forEach(c ->
                    System.out.println("  -> [" + c.getClasseCode() + "] " + c.getClasseName())
            );
        };
    }
}
