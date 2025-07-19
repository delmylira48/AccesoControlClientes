package com.accesoControlClientes.excepciones;

public class UsuarioNoEncontradoException extends AppException{
    public UsuarioNoEncontradoException(Long id){
        super("El usuario con ID: "+ id +" no fue encontrado");
    }

    public UsuarioNoEncontradoException(String username){
        super("El usuario con username: " + username + " no fue encontrado");
    }
}
