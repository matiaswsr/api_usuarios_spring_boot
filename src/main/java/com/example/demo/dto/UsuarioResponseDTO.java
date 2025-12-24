package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nombreCompleto;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
