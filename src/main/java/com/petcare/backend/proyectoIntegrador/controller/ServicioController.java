package com.petcare.backend.proyectoIntegrador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcare.backend.proyectoIntegrador.DTO.ServiceRequestFilters;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioRequest;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.config.S3Service;
import com.petcare.backend.proyectoIntegrador.entity.CaracteristicaValor;
import com.petcare.backend.proyectoIntegrador.entity.Caracteristicas;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.entity.ServicioImagen;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import com.petcare.backend.proyectoIntegrador.service.impl.ServicioServiceImpl;
import com.petcare.backend.proyectoIntegrador.util.DtoConverter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import static com.petcare.backend.proyectoIntegrador.util.DtoConverter.convertirARespuesta;

@RestController
@RequestMapping("api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {
    private static final String UPLOAD_DIR = "uploads/";
    private final IServicioService servicioService;
    private final S3Service s3Service;

    ServicioController(ServicioServiceImpl servicioService, S3Service s3Service) {
        this.servicioService = servicioService;
        this.s3Service = s3Service;
    }

    @PostMapping(value = "/servicio", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> crearServicio(
            @RequestPart("datos") String datosJson,
            @RequestPart("imagenes") List<MultipartFile> imagenes) {

        try {
            // Parse JSON into Servicio DTO
            ServicioRequest servicioDTO = parseServicioRequest(datosJson);

            // Upload images and get URLs
            List<String> imageUrls = processAndUploadImages(imagenes);

            // Map DTO to Servicio entity
            Servicio servicio = mapToEntity(servicioDTO);

            // Save both Servicio and images
            servicioService.crear(servicio, imageUrls);

            return ResponseEntity.ok("Servicio creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el servicio: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponse> obtenerServicioPorId(@PathVariable Integer id) {
        Servicio servicio = servicioService.obtenerPorId(id).orElseThrow();
        ServicioResponse respuesta = convertirARespuesta(servicio);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping
    public Map<String, Object> obtenerServicios(@RequestParam(required = false) List<Long> categoriaIds) {
        List<Servicio> servicios = servicioService.listarTodos();

        // Filtrar por IDs de categoría si se proporcionan
        List<Servicio> serviciosFiltrados = (categoriaIds != null && !categoriaIds.isEmpty())
                ? servicios.stream()
                .filter(servicio -> servicio.getCategoria().getId_categoria() != null &&
                        categoriaIds.contains(servicio.getCategoria().getId_categoria()))
                .toList()
                : servicios;

        // Convertir a DTO
        List<ServicioResponse> respuesta = serviciosFiltrados.stream()
                .map(DtoConverter::convertirARespuesta)
                .collect(Collectors.toList());

        // Crear respuesta con datos adicionales
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("totalServicios", servicios.size());
        resultado.put("serviciosFiltrados", respuesta.size());
        resultado.put("listaServicios", respuesta);

        return resultado;
    }


    @PutMapping("/{servicioId}/categorias/{categoriaId}")
    public ResponseEntity<ServicioResponse> asignarCategoriaAServicio(@PathVariable Integer servicioId, @PathVariable Long categoriaId) {
        Servicio servicio = servicioService.asignarCategoria(servicioId, categoriaId);
        ServicioResponse respuesta = convertirARespuesta(servicio);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PostMapping("/{servicioId}/calificar")
    public ResponseEntity<?> calificarServicio(@PathVariable Integer servicioId, @RequestBody Integer calificacion) {
        servicioService.calificarServicio(servicioId, calificacion);
        return ResponseEntity.ok("Calificación añadida");
    }


    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getSuggestions(@RequestParam(required = false) String query) {
        List<String> suggestions = servicioService.listarSugerencias(query).stream()
                .filter(servicio -> servicio != null &&
                        servicio.toLowerCase().contains(query.toLowerCase()))
                .limit(10)
                .collect(Collectors.toList());

        return ResponseEntity.ok(suggestions);
    }


    @GetMapping("/filters")
    public ResponseEntity<List<Servicio>> getFilteredServices(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String feature,
            @RequestParam(required = false) Integer petsQty,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate singleDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // Build filter object
        ServiceRequestFilters serviceRF = new ServiceRequestFilters();
        serviceRF.setServiceName(name);
        serviceRF.setCategoryName(feature);
        serviceRF.setPetsQty(petsQty);
        serviceRF.setSingleDate(singleDate);
        serviceRF.setStartDate(startDate);
        serviceRF.setEndDate(endDate);

        // Fetch and return filtered services
        List<Servicio> serviciosFiltrados = servicioService.getFilteredServices(serviceRF);
        return ResponseEntity.ok(serviciosFiltrados);
    }



    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Servicio>> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(servicioService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/disponibilidad/{disponibilidad}")
    public ResponseEntity<List<Servicio>> buscarPorDisponibilidad(@PathVariable Boolean disponibilidad) {
        return new ResponseEntity<>(servicioService.buscarPorDisponibilidad(disponibilidad), HttpStatus.OK);
    }

    @GetMapping("/precio/{precioMaximo}")
    public ResponseEntity<List<Servicio>> buscarPorPrecioMenorIgual(@PathVariable BigDecimal precioMaximo) {
        return new ResponseEntity<>(servicioService.buscarPorPrecioMenorIgual(precioMaximo), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Integer id, @RequestBody Servicio servicio) {
        return servicioService.obtenerPorId(id)
                .map(servicioExistente -> {
                    servicio.setIdServicio(id);
                    return new ResponseEntity<>(servicioService.actualizar(servicio), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (servicioService.obtenerPorId(id).isPresent()) {
            servicioService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    private ServicioRequest parseServicioRequest(String datosJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(datosJson, ServicioRequest.class);
    }

    private List<String> processAndUploadImages(List<MultipartFile> imagenes) throws IOException {
        // Example: Upload images to S3 and return URLs
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : imagenes) {
            String url = s3Service.uploadFile(file.getBytes(), file.getOriginalFilename());
            imageUrls.add(url);
        }
        return imageUrls;
    }

    private Servicio mapToEntity(ServicioRequest servicioDTO) {
        Servicio servicio = new Servicio();
        servicio.setNombre(servicioDTO.getNombre());
        servicio.setDescripcion(servicioDTO.getDescripcion());
        servicio.setPrecio(servicioDTO.getPrecio());
        servicio.setCategoria(servicioDTO.getCategoria());

        if (servicioDTO.getCaracteristicas() != null) {
            List<CaracteristicaValor> caracteristicas = servicioDTO.getCaracteristicas().stream()
                    .map(req -> {
                        CaracteristicaValor cv = new CaracteristicaValor();
                        Caracteristicas caracteristica = new Caracteristicas();
                        caracteristica.setIdCaracteristica(req.getIdCaracteristica());
                        cv.setCaracteristica(caracteristica);
                        cv.setValor(req.getValor());
                        return cv;
                    })
                    .toList();

            servicio.setCaracteristicas(caracteristicas);
        }
        return servicio;
    }

} 