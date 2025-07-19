package com.accesoControlClientes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolesDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String nombre;
}
