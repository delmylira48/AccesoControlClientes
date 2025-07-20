package com.accesoControlClientes.servicios.implement;

import com.accesoControlClientes.excepciones.DatosVaciosException;
import com.accesoControlClientes.excepciones.UsuarioRolNoEncontradoException;
import com.accesoControlClientes.excepciones.UsuarioRolYaExisteException;
import com.accesoControlClientes.dominio.Rol;
import com.accesoControlClientes.dominio.Usuario;
import com.accesoControlClientes.dominio.UsuarioRol;
import com.accesoControlClientes.excepciones.UsuarioSinRolesException;
import com.accesoControlClientes.interfaces.UsuarioRolDAO;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.servicios.UsuarioRolServicio;
import com.accesoControlClientes.servicios.UsuarioServicio;
import com.accesoControlClientes.util.ValidadorCampos;
import com.accesoControlClientes.util.ValidadorUsuarioRol;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
class UsuarioRolImplement implements UsuarioRolServicio {

    private final UsuarioRolDAO usuarioRolDAO;
    private final UsuarioServicio usuarioServicio;
    private final RolServicio rolServicio;
    private final ValidadorUsuarioRol validadorUsuarioRol;

    @Override
    @Transactional
    public void guardar(Long usuarioId, Long rolId) {
        log.info("[Guardar UsuarioRol] Intentando guardar UsuarioRol");
        if(validadorUsuarioRol.esVacioRelacionUsuarioRol(usuarioId, rolId)){
            log.warn("[Guardar UsuarioRol] Datos vacios en UsuarioRol");
            throw new DatosVaciosException();
        }

        log.info("[Guardar UsuarioRol] Verificando que la relacion no exista");
        if (!existeRelacion(usuarioId, rolId)) {
            log.info("[Guardar UsuarioRol] La relacion no existia");
            UsuarioRol usuarioRol = new UsuarioRol();
            log.info("[Guardar UsuarioRol] Buscando Usuario");
            usuarioRol.setUsuario(usuarioServicio.buscar(usuarioId));
            log.info("[Guardar UsuarioRol] Buscando rol");
            usuarioRol.setRol(rolServicio.buscar(rolId));
            usuarioRolDAO.save(usuarioRol);
            log.info("[Guardar UsuarioRol] UsuarioRol guardado con id: {}", usuarioRol.getId());
        }else{
            log.warn("[Guardar UsuarioRol] La relacion ya existia");
            throw new UsuarioRolYaExisteException(usuarioId, rolId);
        }
    }

    @Override
    @Transactional
    public void eliminar(Long usuarioId, Long rolId) {
        log.info("[Eliminar UsuarioRol] Intentando eliminar UsuarioRol");
        if(validadorUsuarioRol.esVacioRelacionUsuarioRol(usuarioId, rolId)){
            log.warn("[Eliminar UsuarioRol] Datos vacios en UsuarioRol");
            throw new DatosVaciosException();
        }

        log.info("[Eliminar UsuarioRol] Buscando relacion UsuarioRol");
        var usuarioRol = usuarioRolDAO.findByUsuarioAndRol(usuarioId, rolId).orElse(null);

        if(usuarioRol != null){
            log.info("[Eliminar UsuarioRol] Relacion UsuarioRol encontrada con id: {}", usuarioRol.getId());
            usuarioRolDAO.delete(usuarioRol);
            log.info("[Eliminar UsuarioRol] Relacion UsuarioRol con id: {} eliminado", usuarioRol.getId());
        }else{
            log.warn("[Eliminar UsuarioRol] Relacion UsuarioRol no encontrada");
            throw new UsuarioRolNoEncontradoException(usuarioId, rolId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Rol> obtenerRolesPorUsuario(Long usuarioId) {
        log.info("[Obtener roles por usuario] Intentando obtener roles por usuario con id: {}", usuarioId);
        if(ValidadorCampos.esVacio(usuarioId)){
            log.warn("[Obtener roles por usuario] Datos vacios");
            throw new DatosVaciosException();
        }

        log.info("[Obtener roles por usuario] Buscando roles por usuario con id: {}", usuarioId);
        var roles = usuarioRolDAO.findByUsuario(usuarioId).stream().map(UsuarioRol::getRol).collect(Collectors.toSet());
        if (roles.isEmpty()) {
            log.warn("[Obtener roles por usuario] Usuario con id: {} sin roles asignados", usuarioId);
            throw new UsuarioSinRolesException(usuarioId);
        }
        log.info("[Obtener roles por usuario] Roles encontrados: {}", roles.size());
        return roles;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Usuario> obtenerUsuariosPorRol(Long rolId) {
        log.info("[Obtener usuarios por rol] Intentando obtener usuarios por rol con id: {}", rolId);
        if(ValidadorCampos.esVacio(rolId)){
            throw new DatosVaciosException();
        }
        var usuarios = usuarioRolDAO.findByRol(rolId).stream().map(UsuarioRol::getUsuario).collect(Collectors.toSet());
        log.info("[Obtener usuarios por rol] Usuarios encontrados: {}", usuarios.size());
        return usuarios;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRol buscar(Long usuarioId, Long rolId) {
        log.info("[Buscar UsuarioRol] Intentando buscar relacion UsuarioRol");

        if(validadorUsuarioRol.esVacioRelacionUsuarioRol(usuarioId, rolId)){
            log.warn("[Buscar UsuarioRol] Datos vacios en UsuarioRol");
            throw new DatosVaciosException();
        }

        log.info("[Buscar UsuarioRol] Buscando relacion UsuarioRol");

        return usuarioRolDAO.findByUsuarioAndRol(usuarioId, rolId)
                .orElseThrow( () ->
                {
                    log.warn("[Buscar UsuarioRol] Relacion UsuarioRol no encontrada");
                    return new UsuarioRolNoEncontradoException(usuarioId, rolId);
                });
    }

    @Override
    @Transactional
    public void nuevoUsuarioConRol(Usuario usuario) {
        log.info("[Nuevo usuario con rol] Intentando crear nuevo usuario con rol");
        usuarioServicio.guardar(usuario);
        var rolUser = rolServicio.buscarPorNombre("ROLE_USER");
        guardar(usuario.getId(), rolUser.getId());
        log.info("[Nuevo usuario con rol] Nuevo usuario {} con rol inicial creado", usuario.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuariosSinRol(Long rolId) {
        log.info("[Obtener usuarios sin rol] Intentando obtener usuarios sin rol con id: {}", rolId);
        if(ValidadorCampos.esVacio(rolId)){
            log.warn("[Obtener usuarios sin rol] Datos vacios");
            throw new DatosVaciosException();
        }

        log.info("[Obtener usuarios sin rol] Buscando usuarios sin rol con id: {}", rolId);
        List<Usuario> usuarios = usuarioServicio.listarUsuarios().stream()
                .filter(usuario -> usuario.getUsuarioRolSet().stream()
                        .noneMatch(usuarioRol -> usuarioRol.getRol().getId().equals(rolId))).toList();
        log.info("[Obtener usuarios sin rol] Usuarios encontrados: {}", usuarios.size());
        return usuarios;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeRelacion(Long usuarioId, Long rolId) {
        log.info("[Existe relacion] Intentando validar relacion UsuarioRol");
        if(validadorUsuarioRol.esVacioRelacionUsuarioRol(usuarioId, rolId)){
            log.warn("[Existe relacion] Datos vacios en UsuarioRol");
            throw new DatosVaciosException();
        }
        return usuarioRolDAO.findByUsuarioAndRol(usuarioId, rolId).isPresent();
    }


}
