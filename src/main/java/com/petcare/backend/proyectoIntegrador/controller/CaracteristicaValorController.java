package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.DTO.CaracteristicaValorRequest;
import com.petcare.backend.proyectoIntegrador.entity.CaracteristicaValor;
import com.petcare.backend.proyectoIntegrador.service.ICaracteristicaValorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/caracteristica-valor")
public class CaracteristicaValorController {

    @Autowired
    private ICaracteristicaValorService caracteristicaValorService;

    @PostMapping
    public ResponseEntity<?> crearCaracteristicaValor(@RequestBody CaracteristicaValorRequest request) {
        try {
            CaracteristicaValor nuevoValor = caracteristicaValorService.crear(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoValor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el valor: " + e.getMessage());
        }
    }
}