package com.accesoControlClientes.servicios.implement;

import com.accesoControlClientes.excepciones.DatosVaciosException;
import com.accesoControlClientes.excepciones.UsuarioRolNoEncontradoException;
import com.accesoControlClientes.excepciones.UsuarioRolYaExisteException;
import com.accesoControlClientes.dominio.Rol;
import com.accesoControlClientes.dominio.Usuario;
import com.accesoControlClientes.dominio.UsuarioRol;
import com.accesoControlClientes.interfaces.UsuarioRolDAO;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.servicios.UsuarioRolServicio;
import com.accesoControlClientes.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class UsuarioRolImplement implements UsuarioRolServicio {

    private final UsuarioRolDAO usuarioRolDAO;
    private final UsuarioServicio usuarioServicio;
    private final RolServicio rolServicio;

    @Override
    @Transactional
    public void guardar(Long usuarioId, Long rolId) {
        if(usuarioId == null || rolId == null){
            throw new DatosVaciosException();
        }

        if (!existeRelacion(usuarioId, rolId)) {
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(usuarioServicio.buscar(usuarioId));
            usuarioRol.setRol(rolServicio.buscar(rolId));
            usuarioRolDAO.save(usuarioRol);
        }else{
            throw new UsuarioRolYaExisteException(usuarioId, rolId);
        }
    }

    @Override
    @Transactional
    public void eliminar(Long usuarioId, Long rolId) {
        if(usuarioId == null || rolId == null){
            throw new DatosVaciosException();
        }

        UsuarioRol usuarioRol = buscar(usuarioId, rolId);

        if(usuarioRol != null){
            usuarioRolDAO.delete(usuarioRol);
        }else{
            throw new UsuarioRolNoEncontradoException(usuarioId, rolId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Rol> obtenerRolesPorUsuario(Long usuarioId) {
        if(usuarioId == null){
            throw new DatosVaciosException();
        }

        return usuarioRolDAO.findByUsuario(usuarioId).stream().map(UsuarioRol::getRol).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuariosPorRol(Long rolId) {
        if(rolId == null){
            throw new DatosVaciosException();
        }

        return usuarioRolDAO.findByRol(rolId).stream().map(UsuarioRol::getUsuario).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRol buscar(Long usuarioId, Long rolId) {
        if(usuarioId == null || rolId == null){
            throw new DatosVaciosException();
        }

        return usuarioRolDAO.findByUsuarioAndRol(usuarioId, rolId).orElseThrow( () -> new UsuarioRolNoEncontradoException(usuarioId, rolId));
    }

    @Override
    @Transactional
    public void nuevoUsuarioConRol(Usuario usuario) {
        //Guarda el usuario y hacer validaciones
        usuarioServicio.guardar(usuario);

        var rolUser = rolServicio.buscarPorNombre("ROLE_USER");

        guardar(usuario.getId(), rolUser.getId());

    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuariosSinRol(Long rolId) {
        if(rolId == null){
            throw new DatosVaciosException();
        }

        List<Usuario> usuarios = usuarioServicio.listarUsuarios().stream()
                .filter(usuario -> usuario.getUsuarioRolSet().stream()
                        .noneMatch(usuarioRol -> usuarioRol.getRol().getId().equals(rolId))).toList();

        return usuarios;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeRelacion(Long usuario, Long rol) {
        if(usuario== null || rol == null){
            throw new DatosVaciosException();
        }
        return usuarioRolDAO.findByUsuarioAndRol(usuario, rol).isPresent();
    }


}
