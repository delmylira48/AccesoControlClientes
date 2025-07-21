package com.accesoControlClientes.controlador.helper;

import com.accesoControlClientes.DTO.RolesDTO;
import com.accesoControlClientes.DTO.UsuarioDTO;
import com.accesoControlClientes.DTO.mapper.RolesMapper;
import com.accesoControlClientes.DTO.mapper.UsuarioMapper;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@AllArgsConstructor
@Component
public class UsuarioVistaHelper {

    private final UsuarioServicio usuarioServicio;
    private final RolServicio rolServicio;
    private final UsuarioMapper usuarioMapper;
    private final RolesMapper rolesMapper;

    public void prepararModelosUsuarios(Model model, UsuarioAutenticado usuarioAutenticado){

        List<UsuarioDTO> usuariosDto = usuarioMapper.toDtoList(usuarioServicio.listarUsuarios());
        UsuarioDTO usuarioDto = usuarioMapper.toDto(usuarioAutenticado.getUsuario());
        List<RolesDTO> rolesDTO = rolesMapper.toDtoList(rolServicio.listarRoles());

        model.addAttribute("usuarios", usuariosDto);
        model.addAttribute("roles", rolesDTO);
        model.addAttribute("usuarioAutenticado",  usuarioDto);

    }

    public boolean esUsuarioActual(UsuarioAutenticado usuarioAutenticado, Long id){
        return usuarioAutenticado.getUsuario().getId().equals(id);
    }
}
