package com.accesoControlClientes.excepciones;

public class UsuarioUsernameYaExisteException extends AppException{

    public UsuarioUsernameYaExisteException(String username){
        super("El username: " + username + " ya se encuentra registrado");
    }
}

