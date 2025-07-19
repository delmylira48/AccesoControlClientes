package com.accesoControlClientes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UsuarioDTO {
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long id;

    @NotBlank(message = "El username del usuario no puede ser nulo")
    private String username;

    @NotNull
    private Set<String> roles;
}
