package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.repository.IReservaRepository;
import com.petcare.backend.proyectoIntegrador.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Override
    public Reserva crear(Reserva reserva) {
        reserva.setFechaRegistro(LocalDateTime.now());
        reserva.setFechaActualizacion(LocalDateTime.now());
        reserva.setEsBorrado(false);
        reserva.setEstado("PENDIENTE"); // Valor por defecto al crear
        if (reserva.getFecha() == null) {
            reserva.setFecha(LocalDateTime.now()); // Fecha actual si no se especifica
        }
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> obtenerPorId(Short id) {
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> listarTodos() {
        return reservaRepository.findActivos();
    }

    @Override
    public List<Reserva> listarPorUsuario(Short usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Reserva> listarPorMascota(Short mascotaId) {
        return reservaRepository.findByMascotaId(mascotaId);
    }

    @Override
    public List<Reserva> listarPorEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }

    @Override
    public List<Reserva> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reservaRepository.findByRangoFechas(fechaInicio, fechaFin);
    }

    @Override
    public Reserva actualizar(Reserva reserva) {
        reserva.setFechaActualizacion(LocalDateTime.now());
        return reservaRepository.save(reserva);
    }

    @Override
    public void eliminar(Short id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            Reserva r = reserva.get();
            r.setEsBorrado(true);
            r.setFechaBorrado(LocalDateTime.now());
            reservaRepository.save(r);
        }
    }
}