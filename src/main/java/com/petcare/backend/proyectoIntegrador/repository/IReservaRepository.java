package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, Short> {
    @Query("SELECT r FROM Reserva r WHERE r.usuario.idUsuario = :usuarioId")
    List<Reserva> findByUsuarioId(Short usuarioId);

    @Query("SELECT r FROM Reserva r WHERE r.mascota.idMascota = :mascotaId")
    List<Reserva> findByMascotaId(Short mascotaId);

    List<Reserva> findByEstado(String estado);

    @Query("SELECT r FROM Reserva r WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findByRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("SELECT r FROM Reserva r WHERE r.esBorrado = false")
    List<Reserva> findActivos();
}
