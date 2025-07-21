package com.accesoControlClientes.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioRol> usuarioRolSet = new HashSet<>();

    @Override
    public String toString() {
        List<String> roles = usuarioRolSet.stream().map(rol -> rol.getRol().getNombre()).toList();
        return String.format("Usuario[id=%d, username=%s, roles=%s]", id, username, roles);
    }
}
