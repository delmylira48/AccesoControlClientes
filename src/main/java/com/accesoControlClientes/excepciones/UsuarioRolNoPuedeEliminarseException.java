package com.accesoControlClientes.excepciones;

public class UsuarioRolNoPuedeEliminarseException extends AppException{
    public UsuarioRolNoPuedeEliminarseException(Long idUser, Long idRol){
        super("La relacion de el usuario con id: "+ idUser +" y el rol con id: "+ idRol + " no tiene permitido eliminarse");
    }

    public UsuarioRolNoPuedeEliminarseException(Long idUser, String rol){
        super("La relacion de el usuario con id: "+ idUser +" y el rol: "+ rol + " no tiene permitido eliminarse");
    }
}
