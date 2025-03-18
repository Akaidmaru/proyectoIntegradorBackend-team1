package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.DetallePedido;
import java.util.List;
import java.util.Optional;

public interface IDetallePedidoService {
    DetallePedido crear(DetallePedido detallePedido);
    Optional<DetallePedido> obtenerPorId(Integer id);
    List<DetallePedido> listarTodos();
    List<DetallePedido> listarPorPedido(Integer pedidoId);
    List<DetallePedido> listarPorServicio(Integer servicioId);
    DetallePedido actualizar(DetallePedido detallePedido);
    void eliminar(Integer id);
} 