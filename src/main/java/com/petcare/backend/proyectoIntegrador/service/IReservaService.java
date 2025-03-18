package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.DTO.ReservaDTO;
import com.petcare.backend.proyectoIntegrador.entity.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IReservaService {
    Reserva crear(Reserva reserva);

    Optional<Reserva> obtenerPorId(Integer id);

    List<Reserva> listarTodos();

    List<Reserva> listarPorUsuario(Integer usuarioId);

    List<Reserva> listarPorMascota(Integer mascotaId);

    List<Reserva> listarPorEstado(String estado);

    Reserva actualizar(Reserva reserva);

    void eliminar(Integer id);



    List<LocalDate> getFechasConfirmadas(Integer idServicio);
    Reserva crearReserva(ReservaDTO reservaDTO);
}