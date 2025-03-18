package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Mascota;
import com.petcare.backend.proyectoIntegrador.repository.IMascotaRepository;
import com.petcare.backend.proyectoIntegrador.service.IMascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MascotaServiceImpl implements IMascotaService {

    @Autowired
    private IMascotaRepository mascotaRepository;
    
    @Override
    public Mascota crear(Mascota mascota) {
        mascota.setFechaRegistro(LocalDateTime.now());
        mascota.setFechaActualizacion(LocalDateTime.now());
        mascota.setEsBorrado(false);
        return mascotaRepository.save(mascota);
    }
    
    @Override
    public Optional<Mascota> obtenerPorId(Integer id) {
        return mascotaRepository.findById(id);
    }
    
    @Override
    public List<Mascota> listarTodos() {
        return mascotaRepository.findActivas();
    }
    
    @Override
    public List<Mascota> buscarPorNombre(String nombre) {
        return mascotaRepository.findByNombre(nombre);
    }
    
    @Override
    public List<Mascota> buscarPorEspecie(String especie) {
        return mascotaRepository.findByEspecie(especie);
    }
    
    @Override
    public List<Mascota> listarPorUsuario(Integer usuarioId) {
        return mascotaRepository.findByUsuarioId(usuarioId);
    }
    
    @Override
    public Mascota actualizar(Mascota mascota) {
        mascota.setFechaActualizacion(LocalDateTime.now());
        return mascotaRepository.save(mascota);
    }
    
    @Override
    public void eliminar(Integer id) {
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        if (mascota.isPresent()) {
            Mascota m = mascota.get();
            m.setEsBorrado(true);
            m.setFechaBorrado(LocalDateTime.now());
            mascotaRepository.save(m);
        }
    }
} 