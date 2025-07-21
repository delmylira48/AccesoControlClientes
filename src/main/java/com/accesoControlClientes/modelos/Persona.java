package com.accesoControlClientes.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data //Constructor vacio, metodo iquals, getter, setter, hashcode, toString
@Entity
@Table(name = "persona")
@ToString
public class Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersona")
    private Long id;

    @NotEmpty
    private String nombre;

    @Column(name = "apellido")
    @NotEmpty
    private String apellido1;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String telefono;

}
