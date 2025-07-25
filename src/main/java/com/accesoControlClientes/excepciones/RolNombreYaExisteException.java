package com.accesoControlClientes.excepciones;

public class RolNombreYaExisteException extends AppException{

    public RolNombreYaExisteException(String nombre){
        super("El nombre: " + nombre + " ya se encuentra registrado");
    }
}

