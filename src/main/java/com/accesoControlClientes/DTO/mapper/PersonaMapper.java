package com.accesoControlClientes.DTO.mapper;

import com.accesoControlClientes.DTO.PersonaDTO;
import com.accesoControlClientes.DTO.PersonaGuardarDTO;
import com.accesoControlClientes.modelos.Persona;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {
    PersonaDTO toDto(Persona persona);
    Persona toEntity(PersonaDTO personaDTO);
    Persona toGuardarEntity(PersonaGuardarDTO personaGuardarDTO);

    List<PersonaDTO> toDtoList(List<Persona> personas);
}
