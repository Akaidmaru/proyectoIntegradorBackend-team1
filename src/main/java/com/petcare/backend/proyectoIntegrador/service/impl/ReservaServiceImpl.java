package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.DTO.ReservaDTO;
import com.petcare.backend.proyectoIntegrador.entity.*;
import com.petcare.backend.proyectoIntegrador.repository.*;
import com.petcare.backend.proyectoIntegrador.service.IReservaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements IReservaService {

    private final IReservaRepository reservaRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IServicioRepository servicioRepository;
    private final IMascotaRepository mascotaRepository;
    private final IEspecieRepository especieRepository;


    public ReservaServiceImpl(IReservaRepository reservaRepository, IUsuarioRepository usuarioRepository, IServicioRepository servicioRepository, IMascotaRepository mascotaRepository, IEspecieRepository especieRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
        this.mascotaRepository = mascotaRepository;
        this.especieRepository = especieRepository;
    }

    @Override
    public Reserva crear(Reserva reserva) {
        reserva.setFechaRegistro(LocalDateTime.now());
        reserva.setFechaActualizacion(LocalDateTime.now());
        reserva.setEsBorrado(false);
        reserva.setEstado("CONFIRMADA"); // Valor por defecto al crear
        if (reserva.getFechas() == null) {
            List<ReservaFecha> reservasFecha = new ArrayList<>();
            ReservaFecha reservaFecha = new ReservaFecha();
            reservaFecha.setFecha(LocalDate.now());

            reservasFecha.add(reservaFecha);
        }
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> obtenerPorId(Integer id) {
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> listarTodos() {
        return reservaRepository.findActivos();
    }

    @Override
    public List<Reserva> listarPorUsuario(Integer usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

//    @Override
//    public List<Reserva> listarPorMascota(Integer mascotaId) {
//        return reservaRepository.findByMascotaId(mascotaId);
//    }

    @Override
    public List<Reserva> listarPorEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }

    @Override
    public Reserva actualizar(Reserva reserva) {
        reserva.setFechaActualizacion(LocalDateTime.now());
        return reservaRepository.save(reserva);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            Reserva r = reserva.get();
            r.setEsBorrado(true);
            r.setFechaBorrado(LocalDateTime.now());
            reservaRepository.save(r);
        }
    }

    public List<LocalDate> getFechasConfirmadas(Integer idServicio) {
        return reservaRepository.findFechasConfirmadasByServicio(idServicio);
    }

    public Reserva crearReserva(ReservaDTO reservaDTO) {
        Usuario usuario = usuarioRepository.findById(reservaDTO.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Especie especie = especieRepository.findById(reservaDTO.getIdEspecie())
                .orElseThrow(() -> new IllegalArgumentException("Especie no encontrada"));
        Servicio servicio = servicioRepository.findById(reservaDTO.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        Reserva reserva = new Reserva();
        reserva.setEstado(reservaDTO.getEstado());
        reserva.setFechaRegistro(LocalDateTime.now());
        reserva.setFechaActualizacion(LocalDateTime.now());
        reserva.setUsuario(usuario);
        reserva.setEspecie(especie);
        reserva.setServicio(servicio);

        List<ReservaFecha> fechas = reservaDTO.getFechas().stream().map(fechaDTO -> {
            ReservaFecha reservaFecha = new ReservaFecha();
            reservaFecha.setFecha(LocalDate.parse(fechaDTO.getFecha()));
            reservaFecha.setReserva(reserva);
            return reservaFecha;
        }).collect(Collectors.toList());
        reserva.setFechas(fechas);

        return reservaRepository.save(reserva);
    }
}