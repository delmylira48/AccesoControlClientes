package com.accesoControlClientes.util;

import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuarioRol {

    public boolean esVacioRelacionUsuarioRol(Long idUsuario, Long idRol){
        return ValidadorCampos.esVacio(idUsuario) || ValidadorCampos.esVacio(idRol);
    }
}
