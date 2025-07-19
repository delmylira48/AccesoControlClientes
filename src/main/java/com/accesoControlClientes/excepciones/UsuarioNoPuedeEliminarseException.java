package com.accesoControlClientes.excepciones;

public class UsuarioNoPuedeEliminarseException extends AppException{

    public UsuarioNoPuedeEliminarseException(String username){
        super("El usuario: " + username + " no puede eliminarse");
    }

    public UsuarioNoPuedeEliminarseException(Long id){
        super("El usuario con id: " + id + " no puede eliminarse");
    }
}

