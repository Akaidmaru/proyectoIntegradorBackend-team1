package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IReservaService {
    Reserva crear(Reserva reserva);

    Optional<Reserva> obtenerPorId(Short id);

    List<Reserva> listarTodos();

    List<Reserva> listarPorUsuario(Short usuarioId);

    List<Reserva> listarPorMascota(Short mascotaId);

    List<Reserva> listarPorEstado(String estado);

    List<Reserva> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Reserva actualizar(Reserva reserva);

    void eliminar(Short id);
}
