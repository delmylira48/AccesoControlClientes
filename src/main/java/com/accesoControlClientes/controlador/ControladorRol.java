package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.RolesGuardarDTO;
import com.accesoControlClientes.DTO.mapper.RolesMapper;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.modelos.Rol;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import com.accesoControlClientes.servicios.RolServicio;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorRol {

    private final RolServicio rolServicio;
    private final RolesMapper rolesMapper;

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    private String listarRoles(Model model) {
        prepararModeloRoles(model);
        return "roles";
    }

    @PostMapping("/roles/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    private String guardarRol(@Valid @ModelAttribute("rol") RolesGuardarDTO dto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        log.info("[POST/roles/guardar] Intentando guardando rol");
        if (result.hasErrors()) {
            log.warn("[POST/roles/guardar] Error validando el formulario");
            prepararModeloRoles(model);
            return "roles";
        }
        try{
            log.info("[POST/roles/guardar] Guardando rol");
            var rol = rolesMapper.toGuardarEntity(dto);
            rolServicio.guardar(rol);
            redirectAttributes.addFlashAttribute("success", "Rol guardado con exito");
        }catch(AppException e){
            log.error("[POST/roles/guardar] Error al guardar el rol: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/roles";
    }

    @PostMapping("/roles/eliminar")
    @PreAuthorize("hasRole('ADMIN')")
    private String eliminarRol(@RequestParam("id") Long rol,
                               RedirectAttributes redirectAttributes) {
        log.info("[POST/roles/eliminar] Intentando eliminar rol");
        try{
            rolServicio.eliminar(rol);
            log.info("[POST/roles/eliminar] Rol eliminado. Id: {}", rol);
            redirectAttributes.addFlashAttribute("success", "Rol eliminado con exito");
        }catch (AppException e){
            log.error("[POST/roles/eliminar] Error al eliminar el rol: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/roles";
    }

    private void prepararModeloRoles(Model model) {
        log.info("[GET/roles] listar roles");
        var roles = rolesMapper.toDtoList(rolServicio.listarRoles());
        model.addAttribute("roles", roles);
        log.info("[GET/roles] roles encontrados: {}", roles.size());
        var rol = new RolesGuardarDTO();
        model.addAttribute("rol", rol);
    }
}
