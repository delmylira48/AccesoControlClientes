package com.accesoControlClientes.util;

import com.accesoControlClientes.dominio.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuario {

    public boolean esVacioUsuarioGuardar(Usuario usuario) {
        return ValidadorCampos.esVacio(usuario.getUsername()) || ValidadorCampos.esVacio(usuario.getPassword());
    }

    public boolean esVacioUsuarioActualizar(Usuario usuario) {
        return ValidadorCampos.esVacio(usuario.getUsername()) || ValidadorCampos.esVacio(usuario.getId());
    }

    public void limpiarUsuario(Usuario usuario) {
        usuario.setUsername(usuario.getUsername().trim());
        if (!ValidadorCampos.esVacio(usuario.getPassword())){
            usuario.setPassword(usuario.getPassword().trim());
        }
    }

    public boolean esVacioRelacionUsuarioRol(Long idUsuario, Long idRol){
        return ValidadorCampos.esVacio(idUsuario) || ValidadorCampos.esVacio(idRol);
    }
}
