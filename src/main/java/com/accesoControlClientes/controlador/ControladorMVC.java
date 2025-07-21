package com.accesoControlClientes.controlador;

import com.accesoControlClientes.modelos.Usuario;
import com.accesoControlClientes.servicios.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador MVC
 */
@Controller
@Slf4j
@AllArgsConstructor
public class ControladorMVC {

    private final UsuarioRolServicio usuarioRolServicio;

    @GetMapping("/registro")
    public String registro(Model model){
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@Valid Usuario usuario, Errors errores){
        if(errores.hasErrors()){
            return "registro";
        }
        usuarioRolServicio.nuevoUsuarioConRol(usuario);
        return "redirect:/login";
    }

}
