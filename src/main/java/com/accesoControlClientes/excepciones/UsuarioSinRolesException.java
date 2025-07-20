package com.accesoControlClientes.excepciones;

public class UsuarioSinRolesException extends AppException {
    public UsuarioSinRolesException(Long id) {
        super("El usuario con ID: "+ id +" no tiene roles");
    }
}
