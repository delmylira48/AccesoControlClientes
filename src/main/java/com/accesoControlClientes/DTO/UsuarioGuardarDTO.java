package com.accesoControlClientes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioGuardarDTO {
    @NotNull(message = "El username no puede ser nulo")
    private String username;
    @NotBlank(message = "El password no puede ser nulo")
    private String password;
}
