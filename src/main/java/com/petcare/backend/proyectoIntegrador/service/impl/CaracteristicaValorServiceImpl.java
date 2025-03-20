package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.DTO.CaracteristicaValorRequest;
import com.petcare.backend.proyectoIntegrador.entity.CaracteristicaValor;
import com.petcare.backend.proyectoIntegrador.entity.Caracteristicas;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.repository.ICaracteristicaValorRepository;
import com.petcare.backend.proyectoIntegrador.repository.ICaracteristicasRepository;
import com.petcare.backend.proyectoIntegrador.repository.IServicioRepository;
import com.petcare.backend.proyectoIntegrador.service.ICaracteristicaValorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaracteristicaValorServiceImpl implements ICaracteristicaValorService {

    @Autowired
    private ICaracteristicaValorRepository caracteristicaValorRepository;

    @Autowired
    private ICaracteristicasRepository caracteristicasRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    public CaracteristicaValor crear(CaracteristicaValorRequest request) {
        // Retrieve the Caracteristica
        Caracteristicas caracteristica = caracteristicasRepository.findById(request.getIdCaracteristica())
                .orElseThrow(() -> new RuntimeException("Característica no encontrada"));

        // Retrieve the Servicio
        Servicio servicio = servicioRepository.findById(request.getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // Create the CaracteristicaValor entity
        CaracteristicaValor caracteristicaValor = new CaracteristicaValor();
        caracteristicaValor.setCaracteristica(caracteristica);
        caracteristicaValor.setServicio(servicio);
        caracteristicaValor.setValor(request.getValor());

        // Save to database
        return caracteristicaValorRepository.save(caracteristicaValor);
    }
}

