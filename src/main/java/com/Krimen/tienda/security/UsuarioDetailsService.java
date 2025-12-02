package com.Krimen.tienda.security;

import com.Krimen.tienda.model.Usuario;
import com.Krimen.tienda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String rol = "ROLE_" + u.getRol().name();
        return new User(
                u.getEmail(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority(rol))
        );
    }
}
