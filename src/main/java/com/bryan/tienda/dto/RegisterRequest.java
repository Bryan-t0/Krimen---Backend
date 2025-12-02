package com.bryan.tienda.dto;

import com.bryan.tienda.model.Rol;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private Rol rol;
}
