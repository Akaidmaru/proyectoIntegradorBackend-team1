package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Pedido;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IPedidoService {
    Pedido crear(Pedido pedido);
    Optional<Pedido> obtenerPorId(Integer id);
    List<Pedido> listarTodos();
    List<Pedido> listarPorUsuario(Integer usuarioId);
    List<Pedido> listarPorEstado(String estado);
    List<Pedido> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    Pedido actualizar(Pedido pedido);
    void eliminar(Integer id);
} 