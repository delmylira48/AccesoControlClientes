package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.mapper.UsuarioMapper;
import com.accesoControlClientes.controlador.helper.UsuarioVistaHelper;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.modelos.Usuario;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.servicios.UsuarioRolServicio;
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

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorAdmin {

    private final UsuarioRolServicio usuarioRolServicio;
    private final RolServicio rolServicio;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioVistaHelper usuarioVistaHelper;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String tablaAdmin(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                             Model model){

        log.info("[Get/Admin] Listado de administradores");
        var usuariosAdmin = usuarioRolServicio.obtenerUsuariosPorRol(getRolAdminId());
        log.info("[Get/Admin] Usuarios con rol ADMIN encontrados: {}", usuariosAdmin.size());
        model.addAttribute("usuariosAdmin", usuariosAdmin);

        var usuarios = usuarioRolServicio.obtenerUsuariosSinRol(getRolAdminId());
        log.info("[Get/Admin] Usuarios sin rol ADMIN encontrados: {}", usuarios.size());
        model.addAttribute("usuarios", usuarios);

        //usuario actual
        model.addAttribute("usuarioAutenticado",  usuarioMapper.toDto(usuarioAutenticado.getUsuario()));
        return "admin";
    }

    @PostMapping("/admin/quitarAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String quitarAdmin(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                              @RequestParam("id") Long usuarioId,
                              RedirectAttributes redirectAttributes){

        log.info("[Post/Admin/quitarAdmin] Eliminando rol de usuario");
        try{
            if(usuarioVistaHelper.esUsuarioActual(usuarioAutenticado, usuarioId)){
                log.warn("[Post/Admin/quitarAdmin] Intento de quitar el rol ADMIN al usuario actual");
                throw new AppException("No se puede quitar el rol de ADMIN al usuario actual");
            }else{
                usuarioRolServicio.eliminar(usuarioId, getRolAdminId());
                log.info("[Post/Admin/quitarAdmin] Rol ADMIN de usuario {} eliminado", usuarioId);
                redirectAttributes.addFlashAttribute("success", "Rol ADMIN eliminado correctamente.");
            }
        }catch (AppException e){
            log.error("[Post/Admin/quitarAdmin] Error al quitar rol ADMIN: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/agregarAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String agregarAdmin(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("id") Long id){

        log.info("[Post/Admin/agregarAdmin] Agregando rol ADMIN al usuario {}", id);
        try{
            if(!usuarioVistaHelper.esUsuarioActual(usuarioAutenticado, id)) {
                usuarioRolServicio.guardar(id, getRolAdminId());
                redirectAttributes.addFlashAttribute("success", "Rol ADMIN agregado correctamente.");
            }else{
                log.warn("[Post/Admin/agregarAdmin] Intento de agregar el rol ADMIN al usuario actual");
                throw new AppException("No se puede agregar el rol de ADMIN al usuario actual");
            }
        }catch (AppException e){
            log.error("[Post/Admin/agregarAdmin] Error al agregar rol ADMIN: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    private Long getRolAdminId() {
        return rolServicio.buscarPorNombre("ROLE_ADMIN").getId();
    }

}
