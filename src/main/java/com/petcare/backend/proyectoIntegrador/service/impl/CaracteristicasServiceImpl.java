package com.petcare.backend.proyectoIntegrador.service.impl;
import com.petcare.backend.proyectoIntegrador.entity.Caracteristicas;
import com.petcare.backend.proyectoIntegrador.repository.ICaracteristicasRepository;
import com.petcare.backend.proyectoIntegrador.service.ICaracteristicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaracteristicasServiceImpl implements ICaracteristicasService {

    @Autowired
    private ICaracteristicasRepository caracteristicasRepository;

    // Create a new Caracteristica
    public Caracteristicas crear(Caracteristicas caracteristica) {
        return caracteristicasRepository.save(caracteristica);
    }

    // Retrieve all Caracteristicas
    public List<Caracteristicas> listarTodas() {
        return caracteristicasRepository.findAll();
    }

    // Retrieve a single Caracteristica by ID
    public Caracteristicas obtenerPorId(Integer id) {
        Optional<Caracteristicas> optional = caracteristicasRepository.findById(id);
        return optional.orElse(null);
    }

    // Update an existing Caracteristica
    public Caracteristicas actualizar(Integer id, Caracteristicas caracteristicaActualizada) {
        Optional<Caracteristicas> optional = caracteristicasRepository.findById(id);
        if (optional.isPresent()) {
            Caracteristicas existente = optional.get();
            existente.setNombre(caracteristicaActualizada.getNombre());
            existente.setIcon(caracteristicaActualizada.getIcon());
            return caracteristicasRepository.save(existente);
        }
        return null;
    }

    // Delete a Caracteristica by ID
    public boolean eliminar(Integer id) {
        if (caracteristicasRepository.existsById(id)) {
            caracteristicasRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
