package com.accesoControlClientes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolesGuardarDTO {
    @NotBlank
    private String nombre;
}
