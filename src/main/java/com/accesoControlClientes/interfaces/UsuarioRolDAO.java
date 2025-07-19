package com.accesoControlClientes.interfaces;

import com.accesoControlClientes.dominio.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRolDAO extends JpaRepository<UsuarioRol, Long> {
    @Query("SELECT ur FROM UsuarioRol ur WHERE ur.rol.id= :rol")
    List<UsuarioRol> findByRol(@Param("rol") Long rol);

    @Query("SELECT ur FROM UsuarioRol ur WHERE ur.usuario.id= :usuario")
    List<UsuarioRol> findByUsuario(@Param("usuario") Long usuario);

    @Query("SELECT ur FROM UsuarioRol ur WHERE ur.usuario.id= :usuario AND ur.rol.id = :rol")
    Optional<UsuarioRol> findByUsuarioAndRol(@Param("usuario") Long usuario, @Param("rol") Long rol);
}
