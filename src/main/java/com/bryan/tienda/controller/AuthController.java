package com.bryan.tienda.controller;

import com.bryan.tienda.dto.LoginRequest;
import com.bryan.tienda.dto.RegisterRequest;
import com.bryan.tienda.model.Rol;
import com.bryan.tienda.model.Usuario;
import com.bryan.tienda.security.JwtService;
import com.bryan.tienda.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {


        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje", "Email y password son obligatorios"));
        }

        // Verificar si ya existe
        try {
            Usuario u = new Usuario();
            u.setNombre(request.getNombre());
            u.setEmail(request.getEmail());
            u.setPassword(request.getPassword());
            u.setRol(request.getRol() != null ? request.getRol() : Rol.USER);

            Usuario guardado = usuarioService.registrar(u);

            return ResponseEntity.ok(Map.of(
                    "id", guardado.getId(),
                    "email", guardado.getEmail(),
                    "rol", guardado.getRol()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(Map.of("mensaje", "El correo ya está registrado"));
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("mensaje", "Credenciales inválidas"));
        }

        Usuario u = usuarioService.buscarPorEmail(request.getEmail());
        String token = jwtService.generarToken(u);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", u.getRol(),
                "email", u.getEmail()
        ));
    }
}
