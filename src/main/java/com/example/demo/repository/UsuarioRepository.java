package com.example.demo.repository;

import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
 
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

}

/*
    JpaRepository ya nos da:
        save()
        findAll()
        findById()
        delete()
        findByEmail: lo usaremos para login
        existsByEmail: para validar registro
*/
