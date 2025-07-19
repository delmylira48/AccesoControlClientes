package com.accesoControlClientes.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class cifrado {
    public static void main(String[] args){
        var password = "123";
        System.out.println(password);
        System.out.println(encriptar(password));

    }

    public static String encriptar(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
