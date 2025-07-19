package com.accesoControlClientes.excepciones;

public class DatosVaciosException extends AppException{
    public DatosVaciosException(){
        super("El/Los dato(s) ingresado(s) no contiene(n) informacion");
    }
}
