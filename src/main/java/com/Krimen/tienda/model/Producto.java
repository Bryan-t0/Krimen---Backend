package com.Krimen.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotNull
    @Positive
    private Double precio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @NotNull
    private Boolean oferta = false;

    @NotNull
    @Min(0)
    private Integer stock;

    @Column(length = 500)
    private String image;
}
