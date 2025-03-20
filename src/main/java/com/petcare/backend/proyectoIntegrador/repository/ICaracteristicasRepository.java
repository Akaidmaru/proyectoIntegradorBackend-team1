package com.petcare.backend.proyectoIntegrador.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.petcare.backend.proyectoIntegrador.entity.Caracteristicas;

public interface ICaracteristicasRepository extends JpaRepository<Caracteristicas, Integer> {
}
