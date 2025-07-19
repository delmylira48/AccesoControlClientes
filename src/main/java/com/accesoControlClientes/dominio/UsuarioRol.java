package com.accesoControlClientes.dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioRol implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) //no carga todos los datos de la entidad
    @JoinColumn(name = "idrol") //nombre de la llave foranea
    private Rol rol;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) //no carga todos los datos de la entidad
    @JoinColumn(name = "idusuario") //nombre de la llave foranea
    private Usuario usuario;
}
