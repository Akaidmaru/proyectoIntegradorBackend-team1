package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Pedido;
import com.petcare.backend.proyectoIntegrador.repository.IPedidoRepository;
import com.petcare.backend.proyectoIntegrador.service.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;
    
    @Override
    public Pedido crear(Pedido pedido) {
        pedido.setFechaRegistro(LocalDateTime.now());
        pedido.setFechaActualizacion(LocalDateTime.now());
        pedido.setEsBorrado(false);
        pedido.setEstado("PENDIENTE"); // Valor por defecto al crear
        return pedidoRepository.save(pedido);
    }
    
    @Override
    public Optional<Pedido> obtenerPorId(Integer id) {
        return pedidoRepository.findById(id);
    }
    
    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findActivos();
    }
    
    @Override
    public List<Pedido> listarPorUsuario(Integer usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }
    
    @Override
    public List<Pedido> listarPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }
    
    @Override
    public List<Pedido> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findByRangoFechas(fechaInicio, fechaFin);
    }
    
    @Override
    public Pedido actualizar(Pedido pedido) {
        pedido.setFechaActualizacion(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }
    
    @Override
    public void eliminar(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            Pedido p = pedido.get();
            p.setEsBorrado(true);
            p.setFechaBorrado(LocalDateTime.now());
            pedidoRepository.save(p);
        }
    }
} 