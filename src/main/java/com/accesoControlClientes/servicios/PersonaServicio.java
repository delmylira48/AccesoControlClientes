package com.accesoControlClientes.servicios;

import com.accesoControlClientes.dominio.Persona;

import java.util.List;

public interface PersonaServicio {

    public List<Persona> listarPersonas();

    public void guardar(Persona persona);

    public void eliminar(Long persona);

    public Persona buscar(Long persona);

}
