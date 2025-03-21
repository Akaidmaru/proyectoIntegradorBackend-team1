package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.DTO.UsuarioResponse;
import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre%")
    List<Usuario> findByNombre(String nombre);
    
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRole(ERole role);

    @Query("SELECT u FROM Usuario u WHERE u.esBorrado = false")
    List<Usuario> findActivos();

    @Query("SELECT new com.petcare.backend.proyectoIntegrador.DTO.UsuarioResponse(u.nombre, u.apellido, u.email, u.telefono, u.role) FROM Usuario u WHERE u.esBorrado = false")
    List<UsuarioResponse> findActivosList();


    Usuario save(Usuario usuario);
} 