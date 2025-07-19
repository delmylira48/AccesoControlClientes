package com.accesoControlClientes.interfaces;

import com.accesoControlClientes.dominio.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolDAO extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
