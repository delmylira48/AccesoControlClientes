package com.accesoControlClientes.excepciones;

public class RolNoEncontradoException extends AppException{
    public RolNoEncontradoException(Long id){
        super("El rol con ID: "+ id +" no fue encontrado");
    }

    public RolNoEncontradoException(String nombre){
        super("El rol con nombre: " + nombre + " no fue encontrado");
    }
}
