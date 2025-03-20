package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.entity.Mascota;
import com.petcare.backend.proyectoIntegrador.service.IMascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaController {

    @Autowired
    private IMascotaService mascotaService;

    @PostMapping
    public ResponseEntity<Mascota> crear(@RequestBody Mascota mascota) {
        return new ResponseEntity<>(mascotaService.crear(mascota), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Integer id) {
        return mascotaService.obtenerPorId(id)
                .map(mascota -> new ResponseEntity<>(mascota, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> listarTodos() {
        return new ResponseEntity<>(mascotaService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Mascota>> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(mascotaService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<Mascota>> buscarPorEspecie(@PathVariable String especie) {
        return new ResponseEntity<>(mascotaService.buscarPorEspecie(especie), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Mascota>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return new ResponseEntity<>(mascotaService.listarPorUsuario(usuarioId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizar(@PathVariable Integer id, @RequestBody Mascota mascota) {
        return mascotaService.obtenerPorId(id)
                .map(mascotaExistente -> {
                    mascota.setIdMascota(id);
                    return new ResponseEntity<>(mascotaService.actualizar(mascota), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (mascotaService.obtenerPorId(id).isPresent()) {
            mascotaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 