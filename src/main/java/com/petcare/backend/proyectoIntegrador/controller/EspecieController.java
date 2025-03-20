package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.entity.Especie;
import com.petcare.backend.proyectoIntegrador.entity.Mascota;
import com.petcare.backend.proyectoIntegrador.service.IEspecieService;
import com.petcare.backend.proyectoIntegrador.service.IMascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/especies")
@CrossOrigin(origins = "*")
public class EspecieController {

    @Autowired
    private IEspecieService especieService;

    @PostMapping
    public ResponseEntity<Especie> crear(@RequestBody Especie especie) {
        return new ResponseEntity<>(especieService.crear(especie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especie> obtenerPorId(@PathVariable Integer id) {
        return especieService.obtenerPorId(id)
                .map(mascota -> new ResponseEntity<>(mascota, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Especie>> listarTodos() {
        return new ResponseEntity<>(especieService.listarTodos(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especie> actualizar(@PathVariable Integer id, @RequestBody Especie especie) {
        return especieService.obtenerPorId(id)
                .map(especieExistente -> {
                    especie.setIdEspecie(id);
                    return new ResponseEntity<>(especieService.actualizar(especie), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

