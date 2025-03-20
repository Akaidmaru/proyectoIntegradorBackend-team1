package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.DTO.ServiceRequestFilters;
import com.petcare.backend.proyectoIntegrador.entity.*;
import com.petcare.backend.proyectoIntegrador.repository.*;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl implements IServicioService {
    private static final Logger logger = LoggerFactory.getLogger(ServicioServiceImpl.class);
    private final IServicioRepository servicioRepository;
    private final ICategoriaRepository categoriaRepository;
    private final IReservaRepository reservaRepository;
    private final IServicioImagenRepository servicioImagenRepository;
    private final ICaracteristicaValorRepository caracteristicaValorRepository;

    ServicioServiceImpl(IServicioRepository servicioRepository, ICategoriaRepository categoriaRepository, IReservaRepository reservaRepository, IServicioImagenRepository servicioImagenRepository, ICaracteristicaValorRepository caracteristicaValorRepository) {
        this.servicioRepository = servicioRepository;
        this.categoriaRepository = categoriaRepository;
        this.reservaRepository = reservaRepository;
        this.servicioImagenRepository = servicioImagenRepository;
        this.caracteristicaValorRepository = caracteristicaValorRepository;
    }

    @Override
    public Servicio crear(Servicio servicio, List<String> imageUrls) {
        // Save the Servicio entity (service information)
        servicio.setFechaRegistro(LocalDateTime.now());
        servicio.setFechaActualizacion(LocalDateTime.now());
        servicio.setEsBorrado(false);
        servicio.setEsDisponible(true);
        Servicio savedServicio = servicioRepository.save(servicio);

        logger.info("Servicio saved successfully with ID: {}", savedServicio.getIdServicio());

        if (servicio.getCaracteristicas() != null && !servicio.getCaracteristicas().isEmpty()) {
            for (CaracteristicaValor caracteristicaValor : servicio.getCaracteristicas()) {
                caracteristicaValor.setServicio(savedServicio);
                caracteristicaValorRepository.save(caracteristicaValor);
            }
        }


        // Save the associated ServicioImagen entities (image information)
        List<ServicioImagen> servicioImagenes = imageUrls.stream()
                .map(url -> new ServicioImagen(null, savedServicio, url))
                .toList();

        servicioImagenRepository.saveAll(servicioImagenes);



        return savedServicio;
    }
    
    @Override
    public Optional<Servicio> obtenerPorId(Integer id) {
        return servicioRepository.findById(id);
    }
    
    @Override
    public List<Servicio> listarTodos() {
        return servicioRepository.findActivos();
    }

    @Override
    public List<String> listarSugerencias(String param) {
        return servicioRepository.findSuggestionsByName(param);
    }

    public Servicio asignarCategoria(Integer idServicio, Long categoriaId) {
        Servicio servicio = servicioRepository.findById(idServicio).orElseThrow();
        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow();
        servicio.setCategoria(categoria);
        return servicioRepository.save(servicio);
    }

    @Override
    public List<Servicio> filtrarServicios(String categoria) {
        return servicioRepository.findByCategoria(categoria);
    }

    @Override
    public void calificarServicio(Integer id, Integer calificacion) {
        // Validar que la calificación esté en el rango permitido (1 a 5)
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // Calcular el nuevo rating
        double nuevoRating;
        if (servicio.getTotalValoraciones() == 0) nuevoRating = calificacion;
        else
            nuevoRating = (servicio.getRating() * servicio.getTotalValoraciones() + calificacion) / (servicio.getTotalValoraciones() + 1);

        servicio.setTotalValoraciones(servicio.getTotalValoraciones() + 1);
        servicio.setRating(nuevoRating);

        servicioRepository.save(servicio);
    }


    @Override
    public List<Servicio> getFilteredServices(ServiceRequestFilters serviceRF) {
        return servicioRepository.findAll().stream()
                .filter(servicio -> serviceRF.getServiceName() == null ||
                        (servicio.getNombre() != null && servicio.getNombre().toLowerCase().contains(serviceRF.getServiceName().toLowerCase())))
                .filter(servicio -> serviceRF.getCategoryName() == null ||
                        (servicio.getCategoria() != null && servicio.getCategoria().getNombre() != null &&
                                servicio.getCategoria().getNombre().toLowerCase().contains(serviceRF.getCategoryName().toLowerCase())))
                .filter(servicio -> servicio.getEsDisponible() != null && servicio.getEsDisponible()) // Filter by availability
                .filter(servicio -> {
                    if (serviceRF.getSingleDate() != null) {
                        return isServiceAvailable(servicio, serviceRF.getSingleDate(), serviceRF.getSingleDate());
                    } else if (serviceRF.getStartDate() != null && serviceRF.getEndDate() != null) {
                        return isServiceAvailable(servicio, serviceRF.getStartDate(), serviceRF.getEndDate());
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    private boolean isServiceAvailable(Servicio servicio, LocalDate startDate, LocalDate endDate) {
        // Fetch reservations for the service
        List<Reserva> reservas = reservaRepository.findByServicio(servicio);

        // Check for reservation conflicts
        for (Reserva reserva : reservas) {
            if (!reserva.isEsBorrado() && reserva.getEstado().equalsIgnoreCase("CONFIRMADA")) {
                for(ReservaFecha reservaFecha : reserva.getFechas()){
                    if (!(startDate.isAfter(reservaFecha.getFecha()) || endDate.isBefore(reservaFecha.getFecha()))) {
                        // Overlapping reservation found
                        return false;
                    }
                }
            }
        }
        // No conflicts, service is available
        return true;
    }

        @Override
    public List<Servicio> buscarPorNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }
    
    @Override
    public List<Servicio> buscarPorDisponibilidad(Boolean esDisponible) {
        return servicioRepository.findByEsDisponible(esDisponible);
    }
    
    @Override
    public List<Servicio> buscarPorPrecioMenorIgual(BigDecimal precioMaximo) {
        return servicioRepository.findByPrecioLessThanEqual(precioMaximo);
    }
    
    @Override
    public Servicio actualizar(Servicio servicio) {
        servicio.setFechaActualizacion(LocalDateTime.now());
        return servicioRepository.save(servicio);
    }
    
    @Override
    public void eliminar(Integer id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isPresent()) {
            Servicio s = servicio.get();
            s.setEsBorrado(true);
            s.setFechaBorrado(LocalDateTime.now());
            servicioRepository.save(s);
        }
    }
} 