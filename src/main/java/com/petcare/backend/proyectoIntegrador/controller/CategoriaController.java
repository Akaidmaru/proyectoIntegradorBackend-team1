package com.petcare.backend.proyectoIntegrador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcare.backend.proyectoIntegrador.DTO.CategoriaRequest;
import com.petcare.backend.proyectoIntegrador.DTO.CategoriaResponse;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioSinCategoriaResponse;
import com.petcare.backend.proyectoIntegrador.config.S3Service;
import com.petcare.backend.proyectoIntegrador.entity.Categoria;
import com.petcare.backend.proyectoIntegrador.service.ICategoriaService;
import com.petcare.backend.proyectoIntegrador.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private S3Service s3Service;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        List<CategoriaResponse> respuestas = categorias.stream()
                .map(this::convertirCategoriaARespuesta)
                .collect(Collectors.toList());
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    @GetMapping(value = "/categoria-list")
    public ResponseEntity<List<Categoria>> obtenerListaCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    private CategoriaResponse convertirCategoriaARespuesta(Categoria categoria) {
        List<ServicioSinCategoriaResponse> servicios = categoria.getServicios().stream()
                .map(DtoConverter::convertirARespuestaSinCategoria)
                .collect(Collectors.toList());
        return new CategoriaResponse(
                categoria.getId_categoria(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getImagenUrl(),
                servicios);
    }

    // Método POST mejorado que soporta JSON o multipart/form-data con imagen
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> crearCategoria(
            @RequestPart(value = "datos", required = false) String datosJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            Categoria categoria;

            // Si recibimos datos en formato JSON
            if (datosJson != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                if (imagen != null) {
                    // Caso con imagen y datos JSON
                    CategoriaRequest categoriaDTO = objectMapper.readValue(datosJson, CategoriaRequest.class);
                    categoria = new Categoria();
                    categoria.setNombre(categoriaDTO.getNombre());
                    categoria.setDescripcion(categoriaDTO.getDescripcion());

                    // Subir imagen a S3
                    byte[] fileBytes = imagen.getBytes();
                    String imageUrl = s3Service.uploadFile(fileBytes, imagen.getOriginalFilename());
                    categoria.setImagenUrl(imageUrl);
                } else {
                    // Caso solo con datos JSON
                    categoria = objectMapper.readValue(datosJson, Categoria.class);
                }
            } else if (imagen != null) {
                // Solo imagen sin datos (caso inválido)
                return new ResponseEntity<>("Se requiere el campo 'datos' para crear una categoría",
                        HttpStatus.BAD_REQUEST);
            } else {
                // No se proporcionaron datos ni imagen (caso inválido)
                return new ResponseEntity<>("No se proporcionaron datos para crear la categoría",
                        HttpStatus.BAD_REQUEST);
            }

            Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar la solicitud: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/imagen/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> subirImagenCategoria(
            @PathVariable Long id,
            @RequestPart("imagen") MultipartFile imagen) {

        try {
            Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
            if (categoria == null) {
                return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
            }

            byte[] fileBytes = imagen.getBytes();
            String imageUrl = s3Service.uploadFile(fileBytes, imagen.getOriginalFilename());

            categoria.setImagenUrl(imageUrl);
            categoriaService.crearCategoria(categoria);

            return new ResponseEntity<>("Imagen subida con éxito: " + imageUrl, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al subir la imagen: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtenerCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria != null) {
            CategoriaResponse respuesta = convertirCategoriaARespuesta(categoria);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoriaExistente = categoriaService.obtenerCategoriaPorId(id);
        if (categoriaExistente != null) {
            categoria.setId_categoria(id);
            Categoria categoriaActualizada = categoriaService.crearCategoria(categoria);
            return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria != null) {
            categoriaService.eliminarCategoria(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
