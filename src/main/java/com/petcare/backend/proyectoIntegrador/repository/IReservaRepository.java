package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT r FROM Reserva r WHERE r.usuario.idUsuario = :usuarioId")
    List<Reserva> findByUsuarioId(Integer usuarioId);
    
//    @Query("SELECT r FROM Reserva r WHERE r.mascota.idMascota = :mascotaId")
//    List<Reserva> findByMascotaId(Integer mascotaId);
    
    List<Reserva> findByEstado(String estado);

    @Query("SELECT r FROM Reserva r WHERE r.esBorrado = false")
    List<Reserva> findActivos();


    List<Reserva> findByServicio(Servicio servicio);

    @Query("SELECT rf.fecha FROM ReservaFecha rf WHERE rf.reserva.servicio.idServicio = :idServicio AND rf.reserva.estado = 'CONFIRMADA'")
    List<LocalDate> findFechasConfirmadasByServicio(@Param("idServicio") Integer idServicio);
}
