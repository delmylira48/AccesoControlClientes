package com.accesoControlClientes.security;

import com.accesoControlClientes.modelos.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UsuarioAutenticado implements UserDetails {

    private final Usuario usuario;

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getUsuarioRolSet().stream()
                .map(rol -> new SimpleGrantedAuthority( rol.getRol().getNombre())).toList();
    }

    @Override
    public String toString() {
        List<String> roles = usuario.getUsuarioRolSet().stream().map(rol -> rol.getRol().getNombre()).toList();
        return String.format("UsuarioAutenticado[username=%s, id=%d, roles=%s]",
                getUsername(), getId(), roles);
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Long getId() {
        return usuario.getId();
    }
}
