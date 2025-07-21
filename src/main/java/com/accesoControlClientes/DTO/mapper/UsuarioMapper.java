package com.accesoControlClientes.DTO.mapper;


import com.accesoControlClientes.DTO.UsuarioDTO;
import com.accesoControlClientes.DTO.UsuarioEditarDTO;
import com.accesoControlClientes.DTO.UsuarioGuardarDTO;
import com.accesoControlClientes.modelos.Usuario;
import com.accesoControlClientes.modelos.UsuarioRol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mappings({
            @Mapping(source = "usuarioRolSet", target = "roles", qualifiedByName = "mapRoles")
    })
    UsuarioDTO toDto(Usuario usuario);

    List<UsuarioDTO> toDtoList(List<Usuario> usuarios);

    @Named("mapRoles")
    default Set<String> mapRolesToString(Set<UsuarioRol> roles){
        return roles.stream().map(rol -> rol.getRol().getNombre()).collect(Collectors.toSet());
    }

    Usuario toEntity(UsuarioDTO usuarioDTO);

    Usuario toGuardarEntity(UsuarioGuardarDTO usuarioGuardarDTO);

    Usuario toEditarEntity(UsuarioEditarDTO usuarioEditarDTO);
    UsuarioEditarDTO toEditarDto(Usuario usuario);
}
