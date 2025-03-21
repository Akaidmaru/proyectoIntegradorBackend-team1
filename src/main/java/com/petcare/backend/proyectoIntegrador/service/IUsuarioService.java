package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.DTO.UsuarioResponse;
import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Usuario crear(Usuario usuario);
    Optional<Usuario> obtenerPorId(Integer id);
    List<Usuario> listarTodos();
    List<UsuarioResponse> listarTodosList();
    List<Usuario> buscarPorNombre(String nombre);
    Optional<Usuario> buscarPorEmail(String email);
    Usuario actualizar(Usuario usuario);
    void eliminar(Integer id);
    List<Usuario> listarPorRole(ERole role);
} 