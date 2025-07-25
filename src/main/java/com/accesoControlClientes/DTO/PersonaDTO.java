package com.accesoControlClientes.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido1;
    @NotEmpty
    private String email;
    @NotEmpty
    private String telefono;
}
