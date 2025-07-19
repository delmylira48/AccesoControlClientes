package com.accesoControlClientes.controlador;

import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.dominio.Usuario;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.servicios.UsuarioRolServicio;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorAdmin {

    private final UsuarioRolServicio usuarioRolServicio;
    private final RolServicio rolServicio;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String tablaAdmin(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado, Model model){
        var roladmin= getRolAdminId();
        //usuarios administradores
        var usuariosAdmin = usuarioRolServicio.obtenerUsuariosPorRol(roladmin);
        model.addAttribute("usuarios", usuariosAdmin);

        //usuario no administradores
        List<Usuario> usuarios = usuarioRolServicio.obtenerUsuariosSinRol(roladmin);
        log.info("Usuarios sin rol ADMIN encontrados: {}", usuarios.size());
        model.addAttribute("usuariosN", usuarios);

        //usuario actual
        model.addAttribute("usuarioAutenticado",  usuarioAutenticado.getUsuario());

        return "admin";
    }

    @PostMapping("/admin/quitarAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String quitarAdmin(@RequestParam("id") Long usuarioId){
        usuarioRolServicio.eliminar(usuarioId, getRolAdminId());
        return "redirect:/admin";
    }

    @PostMapping("/admin/agregarAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String agregarAdmin(@RequestParam("id") Long usuario){
        usuarioRolServicio.guardar(usuario, getRolAdminId());
        return "redirect:/admin";
    }

    private Long getRolAdminId() {
        return rolServicio.buscarPorNombre("ROLE_ADMIN").getId();
    }

}
