package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEspecieRepository extends JpaRepository<Especie, Integer> {
}
