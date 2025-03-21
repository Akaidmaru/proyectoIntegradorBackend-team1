package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Mascota;
import java.util.List;
import java.util.Optional;

public interface IMascotaService {
    Mascota crear(Mascota mascota);
    Optional<Mascota> obtenerPorId(Integer id);
    List<Mascota> listarTodos();
    List<Mascota> buscarPorNombre(String nombre);
    List<Mascota> buscarPorEspecie(String especie);
    List<Mascota> listarPorUsuario(Integer usuarioId);
    Mascota actualizar(Mascota mascota);
    void eliminar(Integer id);
} 