package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.RelacionUserRolDTO;
import com.accesoControlClientes.DTO.RolesDTO;
import com.accesoControlClientes.DTO.UsuarioDTO;
import com.accesoControlClientes.DTO.mapper.RolesMapper;
import com.accesoControlClientes.DTO.mapper.UsuarioMapper;
import com.accesoControlClientes.excepciones.UsuarioNoPuedeEliminarseException;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.servicios.UsuarioRolServicio;
import com.accesoControlClientes.servicios.UsuarioServicio;
import com.accesoControlClientes.security.SecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@Validated
public class ControladorUsuarios {

    private final UsuarioServicio usuarioServicio;
    private final RolServicio rolServicio;
    private final UsuarioRolServicio usuarioRolServicio;
    private final SecurityService securityService;
    private final UsuarioMapper usuarioMapper;
    private final RolesMapper rolesMapper;

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public String usuarios(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado, Model model){
        prepararModelosUsuarios(model, usuarioAutenticado);
        return "usuarios";
    }

    @PostMapping("/usuarios/quitarRol")
    @PreAuthorize("hasRole('ADMIN')")
    public String quitarRol(Model model,
                            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                            @Valid RelacionUserRolDTO dto,
                            BindingResult result){

        if (result.hasErrors()) {
            prepararModelosUsuarios(model, usuarioAutenticado);
            return "usuarios";
        }

        var id = dto.getIdUser();
        var rol = rolServicio.buscar(dto.getIdRol());

        try{
            //ELIMINAR: Si el usuario tiene el rol y no es admin
            if(usuarioRolServicio.existeRelacion(id, rol.getId()) && !rol.getNombre().equals("ROLE_ADMIN")){
                usuarioRolServicio.eliminar(id, rol.getId());

                //Si el usuario autenticado es el mismo: Actualizo la sesion
                if(usuarioAutenticado.getUsuario().getId().equals(id)){
                    securityService.refreshSession(usuarioAutenticado.getUsername());
                }
            }

        }catch (AppException e){
            model.addAttribute("error", e.getMessage());
            prepararModelosUsuarios(model, usuarioAutenticado);
            return "usuarios";
        }
        return "redirect:/usuarios";
    }


    @PostMapping( "/usuarios/agregarRol")
    @PreAuthorize("hasRole('ADMIN')")
    public String agregarRol(Model model,
                             @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                             @Valid RelacionUserRolDTO dto,
                             BindingResult result){

        if(result.hasErrors()){
            prepararModelosUsuarios(model, usuarioAutenticado);
            return "usuarios";
        }

        var id = dto.getIdUser();
        var rol = rolServicio.buscar(dto.getIdRol());

        try{
            // GUARDAR: Si el usuario no tiene el rol y el rol no es admin
            if(!usuarioRolServicio.existeRelacion(id, rol.getId()) && !rol.getNombre().equals("ROLE_ADMIN")){
                usuarioRolServicio.guardar(id, rol.getId());

                //Si el usuario autenticado es el mismo: Actualizo la sesion
                if(usuarioAutenticado.getUsuario().getId().equals(id)){
                    securityService.refreshSession(usuarioAutenticado.getUsername());
                }
            }
        }catch (AppException e){
            model.addAttribute("error", e.getMessage());
            prepararModelosUsuarios(model, usuarioAutenticado);
            return "usuarios";
        }

        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/eliminar")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@RequestParam("id") @NotNull Long id,
                           Model model,
                           @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado){

        try{
            //Usuario actual no puede eliminarse
            if(usuarioAutenticado.getUsuario().getId().equals(id)){
                throw new UsuarioNoPuedeEliminarseException(id);
            }
            usuarioServicio.eliminar(id);

        }catch (AppException e){
            model.addAttribute("error", e.getMessage());
            prepararModelosUsuarios(model, usuarioAutenticado);
            return "usuarios";
        }

        return "redirect:/usuarios";
    }

    private void prepararModelosUsuarios(Model model, UsuarioAutenticado usuarioAutenticado){

        List<UsuarioDTO> usuariosDto = usuarioMapper.toDtoList(usuarioServicio.listarUsuarios());
        UsuarioDTO usuarioDto = usuarioMapper.toDto(usuarioAutenticado.getUsuario());
        List<RolesDTO> rolesDTO = rolesMapper.toDtoList(rolServicio.listarRoles());

        model.addAttribute("usuarios", usuariosDto);
        model.addAttribute("roles", rolesDTO);
        model.addAttribute("usuarioAutenticado",  usuarioDto);

    }
}
