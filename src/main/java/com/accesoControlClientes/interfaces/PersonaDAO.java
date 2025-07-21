package com.accesoControlClientes.interfaces;

import com.accesoControlClientes.modelos.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaDAO extends JpaRepository<Persona, Long> {

}
