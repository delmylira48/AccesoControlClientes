package com.accesoControlClientes.util;

import com.accesoControlClientes.dominio.Persona;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPersona {

    public  boolean esVacioPersona(Persona persona){
        return ValidadorCampos.esVacio(persona.getApellido1()) ||
                ValidadorCampos.esVacio(persona.getEmail()) ||
                ValidadorCampos.esVacio(persona.getNombre()) ||
                ValidadorCampos.esVacio(persona.getTelefono());
    }

    public void normalizarPersona(Persona persona){
        persona.setNombre(persona.getNombre().trim());
        persona.setTelefono(persona.getTelefono().trim());
        persona.setApellido1(persona.getApellido1().trim());
        persona.setEmail(persona.getEmail().trim());
    }

}
