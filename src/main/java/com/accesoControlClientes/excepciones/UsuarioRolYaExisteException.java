package com.accesoControlClientes.excepciones;

public class UsuarioRolYaExisteException extends AppException{
    public UsuarioRolYaExisteException(Long idUser, Long idRol){
        super("La relacion de el usuario con id: "+ idUser +" y el rol con id: "+ idRol + " ya existe");
    }

    public UsuarioRolYaExisteException(Long idUser, String rol){
        super("La relacion de el usuario con id: "+ idUser +" y el rol: "+ rol + " ya existe");
    }
}
