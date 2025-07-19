package com.accesoControlClientes.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioEditarDTO {
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long id;
    @NotNull(message = "El username no puede ser nulo")
    private String username;

    private String password;
}
