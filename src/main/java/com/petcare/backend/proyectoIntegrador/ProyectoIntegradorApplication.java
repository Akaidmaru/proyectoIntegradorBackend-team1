package com.petcare.backend.proyectoIntegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
// @EnableJpaRepositories(basePackages =
// "com.petcare.backend.proyectoIntegrador.repository")
@EnableTransactionManagement
public class ProyectoIntegradorApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("spring.datasource.username", dotenv.get("DB_USER"));
		System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
		System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
		System.setProperty("aws.accessKeyId", dotenv.get("AWS_ACCESS_KEY_ID"));
		System.setProperty("aws.secretAccessKey", dotenv.get("AWS_SECRET_ACCESS_KEY"));

		SpringApplication.run(ProyectoIntegradorApplication.class, args);
	}

}
