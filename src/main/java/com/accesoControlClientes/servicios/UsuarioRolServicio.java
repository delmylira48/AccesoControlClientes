package com.accesoControlClientes.servicios;

import com.accesoControlClientes.dominio.Rol;
import com.accesoControlClientes.dominio.Usuario;
import com.accesoControlClientes.dominio.UsuarioRol;

import java.util.List;
import java.util.Set;

public interface UsuarioRolServicio {

    public void guardar(Long usuario, Long rol);

    public void eliminar(Long usuario, Long rol);

    Set<Rol> obtenerRolesPorUsuario(Long usuario);

    List<Usuario> obtenerUsuariosPorRol(Long rol);

    UsuarioRol buscar(Long usuario, Long rol);

    public void nuevoUsuarioConRol(Usuario usuario);

    public List<Usuario> obtenerUsuariosSinRol(Long rol);

    public boolean existeRelacion(Long usuario, Long rol);

}
