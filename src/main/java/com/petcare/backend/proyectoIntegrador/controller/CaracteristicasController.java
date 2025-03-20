package com.petcare.backend.proyectoIntegrador.controller;
import com.petcare.backend.proyectoIntegrador.entity.Caracteristicas;
import com.petcare.backend.proyectoIntegrador.service.ICaracteristicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/caracteristicas")
public class CaracteristicasController {

    @Autowired
    private ICaracteristicasService caracteristicasService;

    // Create a new Caracteristica
    @PostMapping
    public ResponseEntity<Caracteristicas> crearCaracteristica(@RequestBody Caracteristicas caracteristica) {
        Caracteristicas nuevaCaracteristica = caracteristicasService.crear(caracteristica);
        return ResponseEntity.status(201).body(nuevaCaracteristica);
    }

    // Retrieve all Caracteristicas
    @GetMapping
    public ResponseEntity<List<Caracteristicas>> listarCaracteristicas() {
        List<Caracteristicas> caracteristicas = caracteristicasService.listarTodas();
        return ResponseEntity.ok(caracteristicas);
    }

    // Retrieve a single Caracteristica by ID
    @GetMapping("/{id}")
    public ResponseEntity<Caracteristicas> obtenerCaracteristica(@PathVariable Integer id) {
        Caracteristicas caracteristica = caracteristicasService.obtenerPorId(id);
        if (caracteristica == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(caracteristica);
    }

    // Update an existing Caracteristica
    @PutMapping("/{id}")
    public ResponseEntity<Caracteristicas> actualizarCaracteristica(
            @PathVariable Integer id,
            @RequestBody Caracteristicas caracteristicaActualizada) {

        Caracteristicas actualizada = caracteristicasService.actualizar(id, caracteristicaActualizada);
        if (actualizada == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(actualizada);
    }

    // Delete a Caracteristica by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCaracteristica(@PathVariable Integer id) {
        boolean eliminado = caracteristicasService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.status(404).body("Característica no encontrada.");
        }
        return ResponseEntity.ok("Característica eliminada con éxito.");
    }
}
