package com.petcare.backend.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Entidad que representa un servicio ofrecido en el sistema.
 */
@Data
@Entity
@Table(name = "servicio")
@Getter
@Setter
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

    public Servicio(String nombre, String descripcion, BigDecimal precio, String imagenUrlJson) {
    }
}
