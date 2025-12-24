package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;

public interface UsuarioService {

    UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto);

    List<UsuarioResponseDTO> listarUsuarios();
}
