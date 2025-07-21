package com.accesoControlClientes.controlador;

import com.accesoControlClientes.modelos.Rol;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import com.accesoControlClientes.servicios.RolServicio;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ControladorRol {

    private final RolServicio rolServicio;

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    private String listarRoles(Model model) {
        List<Rol> roles = rolServicio.listarRoles();
        model.addAttribute("roles", roles);

        Rol rol = new Rol();
        model.addAttribute("rol", rol);

        return "roles";
    }

    @PostMapping("/roles/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    private String guardarRol(@Valid Rol rol, Errors errores) {
        if (errores.hasErrors()) {
            return "roles";
        }
        rolServicio.guardar(rol);
        return "redirect:/roles";
    }

    @PostMapping("/roles/eliminar")
    @PreAuthorize("hasRole('ADMIN')")
    private String eliminarRol(@RequestParam("id") Long rol) {
        rolServicio.eliminar(rol);
        return "redirect:/roles";
    }

}
