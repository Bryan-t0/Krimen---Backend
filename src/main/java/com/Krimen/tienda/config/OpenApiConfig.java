package com.Krimen.tienda.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                // ===============================
                // 🔥 INFORMACIÓN PROFESIONAL KRIMEN
                // ===============================
                .info(new Info()
                        .title("KRIMEN Store API")
                        .version("1.0")
                        .description("API REST para la Tienda KRIMEN, con autenticación JWT, roles (ADMIN/USER), y CRUD de productos.")
                        .contact(new Contact()
                                .name("Krimen Development Team")
                                .email("contacto@krimen.cl")
                                .url("https://krimen.cl")
                        )
                        .license(new License()
                                .name("Licencia MIT")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )

                // ===============================
                // 🔐 CONFIGURACIÓN DE SEGURIDAD JWT
                // ===============================
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )

                // ===============================
                // 🔒 APLICA JWT A TODOS LOS ENDPOINTS PROTEGIDOS
                // ===============================
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
