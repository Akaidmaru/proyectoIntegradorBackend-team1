package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.repository.IUsuarioRepository;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    @Override
    public Usuario crear(Usuario usuario) {
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setFechaActualizacion(LocalDateTime.now());
        usuario.setEsBorrado(false);
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
    
    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findActivos();
    }
    
    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
    
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    @Override
    public Usuario actualizar(Usuario usuario) {
        usuario.setFechaActualizacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public void eliminar(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setEsBorrado(true);
            u.setFechaBorrado(LocalDateTime.now());
            usuarioRepository.save(u);
        }
    }
    
    @Override
    public List<Usuario> listarPorRole(ERole role) {
        return usuarioRepository.findByRole(role);
    }
} 