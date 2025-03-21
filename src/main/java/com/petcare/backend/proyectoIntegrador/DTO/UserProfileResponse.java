package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private List<ReservaResponse> reservas;
    private List<ServicioResponse> favoritos;

    public UserProfileResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
        this.reservas = usuario.getReservas().stream()
                .map(ReservaResponse::new)
                .collect(Collectors.toList());
        this.favoritos = usuario.getFavoritos().stream()
                .map(ServicioResponse::new)
                .collect(Collectors.toList());
    }
}
