package com.accesoControlClientes.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name= "idrol")
    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true) //relacion 1 a muchos, cuando se elimina un rol se elimina la relacion
    private Set<UsuarioRol> usuarioRolSet = new HashSet<>(); //Long idUsuario;

}
