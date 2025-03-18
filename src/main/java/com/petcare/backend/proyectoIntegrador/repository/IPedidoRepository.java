package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE p.usuario.idUsuario = :usuarioId")
    List<Pedido> findByUsuarioId(Integer usuarioId);
    
    List<Pedido> findByEstado(String estado);
    
    @Query("SELECT p FROM Pedido p WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pedido p WHERE p.esBorrado = false")
    List<Pedido> findActivos();
} 