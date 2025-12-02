package com.Krimen.tienda.config;

import com.Krimen.tienda.model.Producto;
import com.Krimen.tienda.model.Rol;
import com.Krimen.tienda.model.Usuario;
import com.Krimen.tienda.repository.ProductoRepository;
import com.Krimen.tienda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Admin por defecto
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin Krimen");
            admin.setEmail("admin@krimen.cl");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
        }

        // Productos iniciales de la marca KRIMEN
        if (productoRepository.count() == 0) {
            Producto p1 = new Producto();
            p1.setNombre("Polera Krimen Original");
            p1.setPrecio(18990.0);
            productoRepository.save(p1);

            Producto p2 = new Producto();
            p2.setNombre("Polerón Krimen Oversize");
            p2.setPrecio(29990.0);
            productoRepository.save(p2);

            Producto p3 = new Producto();
            p3.setNombre("Gorra Krimen Black Edition");
            p3.setPrecio(14990.0);
            productoRepository.save(p3);
        }
    }
}
