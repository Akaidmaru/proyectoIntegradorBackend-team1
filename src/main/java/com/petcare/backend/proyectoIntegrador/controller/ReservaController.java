package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.config.JwtService;
import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.service.IReservaService;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final IReservaService reservaService;
    private final IUsuarioService usuarioService;
    private final IServicioService servicioService;
    private final JwtService jwtService;

    public ReservaController(IReservaService reservaService, IUsuarioService usuarioService,
            IServicioService servicioService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.servicioService = servicioService;
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.crear(reserva), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable Short id) {
        return reservaService.obtenerPorId(id)
                .map(reserva -> new ResponseEntity<>(reserva, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodos() {
        return new ResponseEntity<>(reservaService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> listarPorUsuario(@PathVariable Short usuarioId) {
        return new ResponseEntity<>(reservaService.listarPorUsuario(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<Reserva>> listarPorMascota(@PathVariable Short mascotaId) {
        return new ResponseEntity<>(reservaService.listarPorMascota(mascotaId), HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reserva>> listarPorEstado(@PathVariable String estado) {
        return new ResponseEntity<>(reservaService.listarPorEstado(estado), HttpStatus.OK);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Reserva>> buscarPorRangoFechas(
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin) {
        return new ResponseEntity<>(
                reservaService.buscarPorRangoFechas(fechaInicio, fechaFin),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(@PathVariable Short id, @RequestBody Reserva reserva) {
        return reservaService.obtenerPorId(id)
                .map(reservaExistente -> {
                    reserva.setIdReserva(id);
                    return new ResponseEntity<>(reservaService.actualizar(reserva), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        if (reservaService.obtenerPorId(id).isPresent()) {
            reservaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{servicioId}")
    public ResponseEntity<String> reservar(@RequestHeader("Authorization") String token,
            @PathVariable short servicioId) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        Servicio servicio = servicioService.obtenerPorId(servicioId).orElseThrow();

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setServicio(servicio);
        reserva.setFecha(LocalDateTime.now());

        reservaService.crear(reserva);
        return ResponseEntity.ok("Reservación realizada");
    }
}