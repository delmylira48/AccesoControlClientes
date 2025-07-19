package com.accesoControlClientes.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class SecurityService {

    private final UserDetailsService userDetailsService;

    public void refreshSession(String username) {
        log.info("[Auth Refresh] Refrescando sesión para usuario: {}", username);
        //Crear el userDetails: usuario autenticado
        UserDetails usuarioAuntenticado = userDetailsService.loadUserByUsername(username);

        //Crear el Authentication
        Authentication newAut = new UsernamePasswordAuthenticationToken(
                usuarioAuntenticado, //principal
                usuarioAuntenticado.getPassword(),
                usuarioAuntenticado.getAuthorities()
        );

        //Actualizar el contexto
        SecurityContextHolder.getContext().setAuthentication(newAut);

        log.info("[Auth Refresh] Contexto de seguridad actualizado para usuario: {}", usuarioAuntenticado);
    }

}
