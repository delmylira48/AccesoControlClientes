package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.UsuarioGuardarDTO;
import com.accesoControlClientes.DTO.mapper.UsuarioMapper;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.servicios.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC
 */
@Controller
@Slf4j
@AllArgsConstructor
public class ControladorRegistro {

    private final UsuarioRolServicio usuarioRolServicio;
    private final UsuarioMapper usuarioMapper;


    @GetMapping("/registro")
    public String registro(Model model){
        model.addAttribute("usuario", new UsuarioGuardarDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@Valid UsuarioGuardarDTO dto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes){
        log.info("[POST /registro] Intentando registrar nuevo usuario: {}", dto.getUsername());

        if(result.hasErrors()){
            log.info("[Post/Registro] Error en el registro");
            return "registro";
        }

        try{
            usuarioRolServicio.nuevoUsuarioConRol(usuarioMapper.toGuardarEntity(dto));
            log.info("[Post/Registro] Usuario registrado con exito");
            redirectAttributes.addFlashAttribute("success", "Usuario registrado con exito");
        }catch(AppException e){
            log.error("[Post/Registro] Error al registrar el usuario: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/login";
    }

}
