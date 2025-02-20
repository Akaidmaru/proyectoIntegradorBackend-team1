package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IMascotaRepository extends JpaRepository<Mascota, Short> {
    @Query("SELECT m FROM Mascota m WHERE m.nombre LIKE %:nombre%")
    List<Mascota> findByNombre(String nombre);
    
    List<Mascota> findByEspecie(String especie);
    
    List<Mascota> findByUsuarioId(Short usuarioId);
    
    @Query("SELECT m FROM Mascota m WHERE m.esBorrado = false")
    List<Mascota> findActivas();
} 