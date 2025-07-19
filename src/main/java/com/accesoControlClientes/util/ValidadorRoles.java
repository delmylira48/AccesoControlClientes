package com.accesoControlClientes.util;

import com.accesoControlClientes.dominio.Rol;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRoles {
    public boolean esVacioRol(Rol rol){
        return ValidadorCampos.esVacio(rol.getNombre()) || ValidadorCampos.esVacio(rol.getId());
    }
}
