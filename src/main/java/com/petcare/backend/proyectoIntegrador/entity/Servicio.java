package com.petcare.backend.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Entidad que representa un servicio ofrecido en el sistema.
 */
@Data
@Entity
@Table(name = "servicio")
@NoArgsConstructor
public class Servicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id_servicio")
    private Integer idServicio; // Preguntar por el tipo de dato, es mejor Integer?
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "es_disponible")
    private Boolean esDisponible;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_borrado")
    private boolean esBorrado;
    
    @Column(name = "fecha_borrado")
    private LocalDateTime fechaBorrado;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "total_valoraciones")
    private Integer totalValoraciones;

    @OneToMany(mappedBy = "servicio")
    private List<DetallePedido> detallePedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    @JsonIgnore
    private Categoria categoria;

    @ManyToMany(mappedBy = "favoritos")
    private List<Usuario> usuariosQueLoMarcaron;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ServicioImagen> imagenUrls;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CaracteristicaValor> caracteristicas;

}
