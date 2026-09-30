@echo off
REM Script de démarrage des microservices Spring Cloud
REM Ordre: Config Server -> Eureka Server -> Microservices -> Gateway

echo ================================================
echo   Demarrage des Microservices Spring Cloud
echo ================================================
echo.

echo [1/5] Demarrage du Config Server (port 9999)...
start "Config Server" cmd /k "cd configserver & mvn spring-boot:run"
timeout /t 15 /nobreak >nul

echo [2/5] Demarrage du Eureka Server (port 8761)...
start "Eureka Server" cmd /k "cd eurekaserver & mvn spring-boot:run"
timeout /t 20 /nobreak >nul

echo [3/5] Demarrage du Classe Microservice (port 8080)...
start "Classe Microservice" cmd /k "cd classe-microservice & mvn spring-boot:run"
timeout /t 15 /nobreak >nul

echo [4/5] Demarrage du Etudiant Microservice (port 8081)...
start "Etudiant Microservice" cmd /k "cd etudiant-microservice & mvn spring-boot:run"
timeout /t 15 /nobreak >nul

echo [5/5] Demarrage du Gateway Server (port 8888)...
start "Gateway Server" cmd /k "cd gatewayserver & mvn spring-boot:run"

echo.
echo ================================================
echo   Tous les services sont en cours de demarrage
echo ================================================
echo.
echo Patientez quelques instants pour que tous les services soient prets...
echo.
echo URLs utiles:
echo   - Eureka Dashboard: http://localhost:8761
echo   - Gateway: http://localhost:8888
echo   - Classe API: http://localhost:8080/api/classes/DI
echo   - Etudiant API: http://localhost:8081/api/etudiants/1
echo.
pause
