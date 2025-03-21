package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.DTO.UserProfileResponse;
import com.petcare.backend.proyectoIntegrador.DTO.UsuarioResponse;
import com.petcare.backend.proyectoIntegrador.config.JwtService;
import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final JwtService jwtService;
    private final IUsuarioService usuarioService;

    public UsuarioController(JwtService jwtService, IUsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.crear(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return new ResponseEntity<>(usuarioService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/usuario-list")
    public ResponseEntity<List<UsuarioResponse>> usuarioList() {
        return new ResponseEntity<>(usuarioService.listarTodosList(), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(usuarioService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Usuario>> listarPorRole(@PathVariable ERole role) {
        return new ResponseEntity<>(usuarioService.listarPorRole(role), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.obtenerPorId(id)
                .map(usuarioExistente -> {
                    usuario.setIdUsuario(id);
                    return new ResponseEntity<>(usuarioService.actualizar(usuario), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/{role}")
    public ResponseEntity<Usuario> actualizarRole(@PathVariable Integer id, @PathVariable ERole role) {
        return usuarioService.obtenerPorId(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setRole(role);
                    return new ResponseEntity<>(usuarioService.actualizar(usuarioExistente), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (usuarioService.obtenerPorId(id).isPresent()) {
            usuarioService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        System.out.println(email);
        Usuario usuario = usuarioService.buscarPorEmail(email).orElseThrow();
        System.out.println(usuario.getEmail());
        System.out.println(usuario.getNombre());
        System.out.println(usuario.getRole());

        return ResponseEntity.ok(new UserProfileResponse(usuario));
    }
} 