package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Caracteristicas;

import java.util.List;
import java.util.Optional;

public interface ICaracteristicasService {
    public Caracteristicas crear(Caracteristicas caracteristica) ;

    public List<Caracteristicas> listarTodas() ;

    public Caracteristicas obtenerPorId(Integer id) ;

    public Caracteristicas actualizar(Integer id, Caracteristicas caracteristicaActualizada) ;

    public boolean eliminar(Integer id) ;
}