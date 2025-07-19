package com.accesoControlClientes.controlador;

import com.accesoControlClientes.dominio.Usuario;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.servicios.UsuarioServicio;
import com.accesoControlClientes.security.SecurityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ControladorCuenta {

    private final UsuarioServicio usuarioServicio;
    private final SecurityService securityService;

    @GetMapping("/cuenta")
    public String cuenta(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado, Model model){
        var usuario = usuarioAutenticado.getUsuario();
        model.addAttribute("usuario", usuario);

        return "cuenta";
    }

    @PostMapping("/cuenta")
    public String actualizar(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado, @Valid Usuario usuario, Errors errors){

        if(errors.hasErrors()){
            return "cuenta";
        }

        var origen = usuarioAutenticado.getUsuario();
        origen.setUsername(usuario.getUsername());

        if(!usuario.getPassword().isEmpty()){
            origen.setPassword(usuario.getPassword());
        }

        usuarioServicio.guardar(origen);

        //Refrescar la sesión con nuevo username
        securityService.refreshSession(origen.getUsername());

        return "redirect:/cuenta";
    }

}
