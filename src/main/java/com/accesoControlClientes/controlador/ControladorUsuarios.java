package com.accesoControlClientes.controlador;

import com.accesoControlClientes.controlador.helper.UsuarioVistaHelper;
import com.accesoControlClientes.excepciones.UsuarioNoPuedeEliminarseException;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.servicios.UsuarioServicio;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorUsuarios {

    private final UsuarioServicio usuarioServicio;
    private final UsuarioVistaHelper usuarioVistaHelper;

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public String usuarios(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                           Model model){
        log.info("[Get/Usuarios] Listado de usuarios");
        usuarioVistaHelper.prepararModelosUsuarios(model, usuarioAutenticado);
        return "usuarios";
    }

    @PostMapping("/usuarios/eliminar")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@RequestParam("id") Long id,
                           @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                           RedirectAttributes redirectAttributes){

        log.info("[Post/Usuarios/eliminar] Eliminando usuario con id: {}", id);
        try{
            if(usuarioVistaHelper.esUsuarioActual(usuarioAutenticado, id)){
                log.warn("[Post/Usuarios/eliminar] Intento de eliminarse el mismo usuario");
                throw new UsuarioNoPuedeEliminarseException(id);
            }
            usuarioServicio.eliminar(id);
            log.info("[Post/Usuarios/eliminar] Eliminado usuario con id: {}", id);

        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/usuarios";
    }

}
