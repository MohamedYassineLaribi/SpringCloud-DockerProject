package com.laribi.etudiant;

import com.laribi.etudiant.entities.Etudiant;
import com.laribi.etudiant.repos.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
public class EtudiantApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtudiantApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    CommandLineRunner initData(EtudiantRepository etudiantRepository) {
        return args -> {
            etudiantRepository.save(Etudiant.builder()
                    .firstName("Mohamed")
                    .lastName("Laribi")
                    .classeCode("DI")
                    .build());

            etudiantRepository.save(Etudiant.builder()
                    .firstName("Ahmed")
                    .lastName("Ben Ali")
                    .classeCode("SI")
                    .build());

            System.out.println("=== Étudiants chargés avec succès ===");
            etudiantRepository.findAll().forEach(e ->
                    System.out.println("  -> [" + e.getId() + "] "
                            + e.getFirstName() + " " + e.getLastName()
                            + " | classeCode=" + e.getClasseCode())
            );
        };
    }
}
