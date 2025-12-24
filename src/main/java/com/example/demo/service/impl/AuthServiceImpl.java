package com.example.demo.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtProperties;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.auth.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtProperties props;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHashed())) {
            throw new BusinessException("Credenciales inválidas");
        }

        String token = jwtUtil.generarToken(usuario.getEmail());

        return LoginResponseDTO.builder()
            .token(token)
            .tipo("Bearer")
            .expiresIn(props.getExpiration())
            .build();
    }
}
