package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.config.JwtService;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favoritos")
@CrossOrigin(origins = "*")
public class FavoritosController {

    private final IUsuarioService usuarioService;
    private final IServicioService servicioService;
    private final JwtService jwtService;

    public FavoritosController(IUsuarioService usuarioService, IServicioService servicioService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.servicioService = servicioService;
    }

    @PostMapping("/{servicioId}")
    public ResponseEntity<String> toggleFavorito(@RequestHeader("Authorization") String token,
                                                 @PathVariable Integer servicioId) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        Servicio servicio = servicioService.obtenerPorId(servicioId).orElseThrow();

        if (usuario.getFavoritos().contains(servicio)) {
            usuario.getFavoritos().remove(servicio);
        } else {
            usuario.getFavoritos().add(servicio);
        }

        usuarioService.crear(usuario);
        return ResponseEntity.ok("Favorito actualizado");
    }
}