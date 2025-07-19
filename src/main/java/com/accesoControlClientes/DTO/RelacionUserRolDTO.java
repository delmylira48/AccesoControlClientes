package com.accesoControlClientes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelacionUserRolDTO {
    @NotNull(message = "El id del usuario no puede ser nulo")
    private Long idUser;
    @NotNull(message = "El id del rol no puede ser nulo")
    private Long idRol;
}
