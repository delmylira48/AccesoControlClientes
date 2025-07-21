package com.accesoControlClientes.servicios;


import com.accesoControlClientes.modelos.Rol;

import java.util.List;

public interface RolServicio {

    public List<Rol> listarRoles();

    public void guardar(Rol rol);

    public void eliminar(Long rol);
    
    public Rol buscar(Long rol);

    public Rol buscarPorNombre(String nombre);

    public List<String> obtenerNombresRoles();
}
