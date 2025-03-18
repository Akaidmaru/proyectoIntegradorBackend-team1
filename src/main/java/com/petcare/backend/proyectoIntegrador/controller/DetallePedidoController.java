package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.entity.DetallePedido;
import com.petcare.backend.proyectoIntegrador.service.IDetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-pedido")
@CrossOrigin(origins = "*")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detallePedidoService;

    @PostMapping
    public ResponseEntity<DetallePedido> crear(@RequestBody DetallePedido detallePedido) {
        return new ResponseEntity<>(detallePedidoService.crear(detallePedido), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Integer id) {
        return detallePedidoService.obtenerPorId(id)
                .map(detalle -> new ResponseEntity<>(detalle, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<DetallePedido>> listarTodos() {
        return new ResponseEntity<>(detallePedidoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<DetallePedido>> listarPorPedido(@PathVariable Integer pedidoId) {
        return new ResponseEntity<>(detallePedidoService.listarPorPedido(pedidoId), HttpStatus.OK);
    }

    @GetMapping("/servicio/{servicioId}")
    public ResponseEntity<List<DetallePedido>> listarPorServicio(@PathVariable Integer servicioId) {
        return new ResponseEntity<>(detallePedidoService.listarPorServicio(servicioId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Integer id, @RequestBody DetallePedido detallePedido) {
        return detallePedidoService.obtenerPorId(id)
                .map(detalleExistente -> {
                    detallePedido.setIdDetallePedido(id);
                    return new ResponseEntity<>(detallePedidoService.actualizar(detallePedido), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (detallePedidoService.obtenerPorId(id).isPresent()) {
            detallePedidoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 