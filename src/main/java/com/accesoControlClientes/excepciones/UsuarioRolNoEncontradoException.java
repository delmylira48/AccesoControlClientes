package com.accesoControlClientes.excepciones;

public class UsuarioRolNoEncontradoException extends AppException{
    public UsuarioRolNoEncontradoException(Long idUser, Long idRol){
        super("La relacion de el usuario con id: "+ idUser +" y el rol con id: "+ idRol + " no fue encontrado");
    }

    public UsuarioRolNoEncontradoException(Long idUser, String rol){
        super("La relacion de el usuario con id: "+ idUser +" y el rol: "+ rol + " no fue encontrado");
    }
}
