package com.accesoControlClientes.excepciones;


public class AppException extends RuntimeException {
    // Constructor con mensaje personalizado
    public AppException(String mensaje) {
        super(mensaje);
    }

    public AppException(){
        super();
    }
}
