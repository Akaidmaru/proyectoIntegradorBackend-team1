package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponseList;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponseSuggestions;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    @Query("SELECT s FROM Servicio s WHERE (:categoria IS NULL OR s.categoria = :categoria)")
    List<Servicio> findByCategoria(@Param("categoria") String categoria);


    @Query("SELECT s FROM Servicio s WHERE s.nombre LIKE %:nombre%")
    List<Servicio> findByNombre(String nombre);
    
    List<Servicio> findByEsDisponible(Boolean esDisponible);
    
    @Query("SELECT s FROM Servicio s WHERE s.precio <= :precioMaximo")
    List<Servicio> findByPrecioLessThanEqual(BigDecimal precioMaximo);

    @Query("SELECT s FROM Servicio s WHERE s.esBorrado = false")
    List<Servicio> findActivos();

    @Query("SELECT new com.petcare.backend.proyectoIntegrador.DTO.ServicioResponseList(s.idServicio, s.descripcion, s.nombre, c.id_categoria, c.nombre) " +
           "FROM Servicio s JOIN s.categoria c ")
    List<ServicioResponseList> findServiciosActivosList();

    @Query("SELECT new com.petcare.backend.proyectoIntegrador.DTO.ServicioResponseSuggestions( " +
            "c.id_categoria, c.nombre, s.idServicio, s.nombre, MIN(img.imagenUrl)) " +
            "FROM Servicio s " +
            "JOIN s.categoria c " +
            "LEFT JOIN s.imagenUrls img " +
            "WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND s.esBorrado = false " +
            "GROUP BY c.id_categoria, c.nombre, s.idServicio, s.nombre")
    List<ServicioResponseSuggestions> findSuggestionsByCategoriaName(@Param("query") String query);



    @Query("SELECT s FROM Servicio s JOIN FETCH s.categoria c WHERE s.esBorrado = false")
    List<Servicio> findAllWithCategoria();


} 