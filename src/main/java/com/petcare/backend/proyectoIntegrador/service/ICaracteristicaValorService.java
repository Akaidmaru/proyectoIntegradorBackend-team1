package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.DTO.CaracteristicaValorRequest;
import com.petcare.backend.proyectoIntegrador.entity.CaracteristicaValor;

public interface ICaracteristicaValorService {
    CaracteristicaValor crear(CaracteristicaValorRequest request);
}
