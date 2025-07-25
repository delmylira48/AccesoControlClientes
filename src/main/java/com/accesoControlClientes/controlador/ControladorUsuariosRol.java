package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.RelacionUserRolDTO;
import com.accesoControlClientes.controlador.helper.UsuarioVistaHelper;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.security.SecurityService;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.servicios.UsuarioRolServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@AllArgsConstructor
@Slf4j
public class ControladorUsuariosRol {

    private final RolServicio rolServicio;
    private final UsuarioRolServicio usuarioRolServicio;
    private final SecurityService securityService;
    private final UsuarioVistaHelper usuarioVistaHelper;

    @PostMapping("/quitarRol")
    @PreAuthorize("hasRole('ADMIN')")
    public String quitarRol(Model model,
                            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                            @Valid RelacionUserRolDTO dto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes){

        log.info("[Post/Usuarios/quitarRol] Eliminando rol con id {} de usuario con id {}", dto.getIdRol(), dto.getIdUser());

        if (result.hasErrors()) {
            log.warn("[Post/Usuarios/quitarRol] Error validando el formulario");
            usuarioVistaHelper.prepararModelosUsuarios(model, usuarioAutenticado);
            return "usuarios";
        }

        var id = dto.getIdUser();
        var idRol = dto.getIdRol();

        try{
            if(!esRolProtegido(idRol)){
                usuarioRolServicio.eliminar(id, idRol);
                redirectAttributes.addFlashAttribute("success", "Rol eliminado con exito");
                log.info("[Post/Usuarios/quitarRol] Eliminado rol con id {} de usuario con id {}", idRol, id);
                if(usuarioVistaHelper.esUsuarioActual(usuarioAutenticado, id)){
                    log.info("[Post/Usuarios/quitarRol] Actualizando sesion");
                    securityService.refreshSession(usuarioAutenticado.getUsername());
                }
            }else{
                log.warn("[Post/Usuarios/quitarRol] Intento de quitar el rol ADMIN/USER al usuario ID: {}", id);
                throw new AppException("No se puede quitar el rol de ADMIN/USER al usuario");
            }

        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }


    @PostMapping( "/agregarRol")
    @PreAuthorize("hasRole('ADMIN')")
    public String agregarRol(Model model,
                             @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                             @Valid RelacionUserRolDTO dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){

        log.info("[Post/Usuarios/agregarRol] Agregando rol con id {} a usuario con id {}", dto.getIdRol(), dto.getIdUser());

        if(result.hasErrors()){
            log.warn("[Post/Usuarios/agregarRol] Error validando el formulario");
            usuarioVistaHelper.prepararModelosUsuarios(model, usuarioAutenticado);
            return "usuarios";
        }

        var id = dto.getIdUser();
        var rolId = dto.getIdRol();

        try{
            if(!esRolProtegido(rolId)){
                usuarioRolServicio.guardar(id, rolId);
                redirectAttributes.addFlashAttribute("success", "Rol agregado con exito");
                log.info("[Post/Usuarios/agregarRol] Agregado rol con id {} a usuario con id {}", rolId, id);
                //Si el usuario autenticado es el mismo: Actualizo la sesion
                if(usuarioVistaHelper.esUsuarioActual(usuarioAutenticado, id)){
                    log.info("[Post/Usuarios/agregarRol] Actualizando sesion");
                    securityService.refreshSession(usuarioAutenticado.getUsername());
                }
            }else{
                log.warn("[Post/Usuarios/agregarRol] Intento de agregar el rol ADMIN al usuario ID: {}", id);
                throw new AppException("No se puede agregar el rol de ADMIN");
            }
        }catch (AppException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/usuarios";
    }


    private boolean esRolProtegido(Long idRol) {
        String nombre = rolServicio.buscar(idRol).getNombre();
        return "ROLE_ADMIN".equals(nombre) || "ROLE_USER".equals(nombre);
    }

}
