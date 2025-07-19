package com.accesoControlClientes.servicios;

import com.accesoControlClientes.dominio.Usuario;

import java.util.List;

public interface UsuarioServicio {

    public List<Usuario> listarUsuarios();

    public void guardar(Usuario usuario);

    public void actualizar(Usuario usuario);

    public void eliminar(Long id);

    public Usuario buscar(Long id);

    public Usuario buscarPorUsername(String username);

    public boolean existePorUsername(String username);

}
