package com.petcare.backend.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicio_imagenes")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicioImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer idServicioImg;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    @JsonIgnore
    private Servicio servicio;

    @Column(name = "imagen_url", nullable = false)
    @NotNull
    @Size(max = 255)
    private String imagenUrl;

}



