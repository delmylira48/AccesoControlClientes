package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.UsuarioEditarDTO;
import com.accesoControlClientes.DTO.mapper.UsuarioMapper;
import com.accesoControlClientes.controlador.helper.UsuarioVistaHelper;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.security.SecurityService;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.servicios.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorLogin {

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado, Model model) {
        if (usuarioAutenticado != null) {
            return "redirect:/home";
        }else{
            return "login";
        }
    }

    @GetMapping("/")
    public String inicio(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado, Model model) {
        if (usuarioAutenticado != null) {
            return "redirect:/home";
        }else{
            return "index";
        }
    }

}
