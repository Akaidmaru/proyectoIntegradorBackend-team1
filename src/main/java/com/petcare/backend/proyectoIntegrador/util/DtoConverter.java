package com.petcare.backend.proyectoIntegrador.util;

import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioSinCategoriaResponse;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;

public class DtoConverter {

    public static ServicioResponse convertirARespuesta(Servicio servicio) {
        return new ServicioResponse(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getImagenUrls(),
                servicio.getEsDisponible(),
                servicio.getFechaRegistro(),
                servicio.getFechaActualizacion(),
                servicio.isEsBorrado(),
                servicio.getFechaBorrado(),
                servicio.getCategoria(),
                servicio.getRating()
        );
    }

    public static ServicioSinCategoriaResponse convertirARespuestaSinCategoria(Servicio servicio) {
        return new ServicioSinCategoriaResponse(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getImagenUrls(),
                servicio.getEsDisponible(),
                servicio.getFechaRegistro(),
                servicio.getFechaActualizacion(),
                servicio.isEsBorrado(),
                servicio.getFechaBorrado(),
                servicio.getRating()
        );
    }

}
