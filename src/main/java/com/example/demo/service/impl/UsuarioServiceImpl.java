package com.example.demo.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("El email ya está registrado");
        }

        Usuario usuario = Usuario.builder()
            .nombreCompleto(dto.getNombreCompleto())
            .email(dto.getEmail())
            .passwordHashed(passwordEncoder.encode(dto.getPassword()))
            .build();

        Usuario saved = usuarioRepository.save(usuario);

        return UsuarioResponseDTO.builder()
            .id(saved.getId())
            .nombreCompleto(saved.getNombreCompleto())
            .email(saved.getEmail())
            .createdAt(saved.getCreatedAt())
            .updatedAt(saved.getUpdatedAt())
            .build();
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
            .stream().map(u -> UsuarioResponseDTO.builder()
                .id(u.getId())
                .nombreCompleto(u.getNombreCompleto())
                .email(u.getEmail())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build()).toList();
    }
}

