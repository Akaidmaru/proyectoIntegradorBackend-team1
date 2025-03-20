package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Especie;
import com.petcare.backend.proyectoIntegrador.repository.IEspecieRepository;
import com.petcare.backend.proyectoIntegrador.service.IEspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieImpl implements IEspecieService {

    private IEspecieRepository especieRepository;

    public EspecieImpl(IEspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    @Override
    public Especie crear(Especie especie) {
        return especieRepository.save(especie);
    }

    @Override
    public Optional<Especie> obtenerPorId(Integer id) {
        return especieRepository.findById(id);
    }

    @Override
    public List<Especie> listarTodos() {
        return especieRepository.findAll();
    }

    @Override
    public Especie actualizar(Especie especie) {
        return especieRepository.save(especie);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Especie> especie = especieRepository.findById(id);
        if (especie.isPresent()) {
            Especie e = especie.get();
            especieRepository.save(e);
        }
    }
}
