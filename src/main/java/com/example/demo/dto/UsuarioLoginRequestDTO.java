package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class UsuarioLoginRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}