package com.accesoControlClientes.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaGuardarDTO {
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
