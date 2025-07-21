package com.accesoControlClientes.interfaces;

import com.accesoControlClientes.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

}
