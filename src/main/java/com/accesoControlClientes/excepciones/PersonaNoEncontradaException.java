package com.accesoControlClientes.excepciones;

public class PersonaNoEncontradaException extends AppException{
    public PersonaNoEncontradaException(Long id){
        super("La persona con ID: "+ id +" no fue encontrada");
    }

}
