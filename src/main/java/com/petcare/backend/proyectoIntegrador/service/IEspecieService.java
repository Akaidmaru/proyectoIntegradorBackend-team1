package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Especie;
import java.util.List;
import java.util.Optional;

public interface IEspecieService {

        Especie crear(Especie especie);
        Optional<Especie> obtenerPorId(Integer id);
        List<Especie> listarTodos();
        Especie actualizar(Especie especie);
        void eliminar(Integer id);

}
