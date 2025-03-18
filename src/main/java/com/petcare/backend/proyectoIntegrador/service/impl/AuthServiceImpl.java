package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.DTO.AuthResponse;
import com.petcare.backend.proyectoIntegrador.DTO.LoginRequest;
import com.petcare.backend.proyectoIntegrador.DTO.RegisterRequest;
import com.petcare.backend.proyectoIntegrador.config.JwtService;
import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.service.IAuthService;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    private final IUsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;

    public AuthServiceImpl(IUsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtService jwtService, JavaMailSender mailSender) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mailSender = mailSender;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioService.buscarPorEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(null, null, null, null, null, null, "El correo ya está registrado.");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .role(request.getRole() != null ? request.getRole() : ERole.CLIENTE)
                .telefono(request.getTelefono())
                .fechaRegistro(LocalDateTime.now())
                .build();

        usuarioService.crear(usuario);
        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponse(token, usuario.getRole(), usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(), "Usuario creado correctamente", null);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(request.getEmail());
        if (usuario.isEmpty()) {
            return new AuthResponse(null, null, null, null, null, null, "Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getContrasenia(), usuario.get().getContrasenia())) {
            return new AuthResponse(null, null, null, null, null, null, "Credenciales inválidas");
        }

        String token = jwtService.generateToken(usuario.get().getEmail());
        return new AuthResponse(token, usuario.get().getRole(), usuario.get().getIdUsuario(), usuario.get().getNombre(), usuario.get().getApellido(), "Usuario autenticado correctamente", null);
    }

    @Override
    public void enviarCorreoConfirmacion(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(mensaje);
    }
}
