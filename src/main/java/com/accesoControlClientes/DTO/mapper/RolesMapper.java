package com.accesoControlClientes.DTO.mapper;

import com.accesoControlClientes.DTO.RolesDTO;
import com.accesoControlClientes.DTO.RolesGuardarDTO;
import com.accesoControlClientes.modelos.Rol;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolesMapper {
    RolesDTO toDto(Rol rol);
    Rol toGuardarEntity(RolesGuardarDTO rolesDTO);

    List<RolesDTO> toDtoList(List<Rol> roles);

}
