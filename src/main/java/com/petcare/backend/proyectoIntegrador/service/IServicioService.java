package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.DTO.ServiceRequestFilters;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponseList;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponseSuggestions;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IServicioService {
    Servicio crear(Servicio servicio, List<String> imageUrls);
    Optional<Servicio> obtenerPorId(Integer id);
    List<Servicio> listarTodos();
    List<ServicioResponseList> listarTodosList();
    List<ServicioResponseSuggestions> listarSugerencias(String param);
    List<Servicio> getFilteredServices(ServiceRequestFilters serviceRequestFilters);
    Servicio asignarCategoria(Integer idServicio, Long idCategoria);
    List<Servicio> filtrarServicios(String categoria);
    void calificarServicio(Integer id, Integer calificacion);


    List<Servicio> buscarPorNombre(String nombre);
    List<Servicio> buscarPorDisponibilidad(Boolean esDisponible);
    List<Servicio> buscarPorPrecioMenorIgual(BigDecimal precioMaximo);
    Servicio actualizar(Servicio servicio);
    void eliminar(Integer id);
} 