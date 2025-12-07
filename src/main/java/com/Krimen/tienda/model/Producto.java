package com.Krimen.tienda.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;
    private String categoria;
    private Boolean oferta;
    private Integer stock;

    @Column(length = 500)
    private String image;
}
