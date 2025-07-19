package com.accesoControlClientes.util;

public class ValidadorCampos {
    public static boolean esVacio(String campo) {
        return campo == null || campo.isBlank();
    }

    public static boolean esVacio(Long campo) {
        return campo == null;
    }

}
