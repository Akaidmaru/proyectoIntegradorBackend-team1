package com.petcare.backend.proyectoIntegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
// @EnableJpaRepositories(basePackages =
// "com.petcare.backend.proyectoIntegrador.repository")
@EnableTransactionManagement
public class ProyectoIntegradorApplication {

	public static void main(String[] args) {
		// Verificar si estamos en entorno de producción
		String activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");
		boolean isProduction = activeProfile != null && activeProfile.equals("prod");

		// Por defecto, usar el archivo .env a menos que explícitamente estemos en
		// producción
		if (!isProduction) {
			try {
				Dotenv dotenv = Dotenv.load();

				System.setProperty("spring.datasource.url", "jdbc:mysql://" + dotenv.get("DB_HOST") + ":3306/"
						+ dotenv.get("DB_NAME")
						+ "?createDatabaseIfNotExist=true&useSSL=false&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true");
				System.setProperty("spring.datasource.username", dotenv.get("DB_USER"));
				System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
				System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
				System.setProperty("aws.accessKeyId", dotenv.get("AWS_ACCESS_KEY_ID"));
				System.setProperty("aws.secretAccessKey", dotenv.get("AWS_SECRET_ACCESS_KEY"));

				System.out.println("Variables cargadas desde archivo .env (entorno local/desarrollo)");
			} catch (Exception e) {
				System.out.println("Error al cargar variables de .env: " + e.getMessage());
			}
		} else {
			System.out.println("Ejecutando en entorno de producción, usando variables de Docker");
			// En producción, las variables de entorno son proporcionadas por Docker
		}

		SpringApplication.run(ProyectoIntegradorApplication.class, args);
	}

}
