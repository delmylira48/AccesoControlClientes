package com.accesoControlClientes.servicios.implement;

import com.accesoControlClientes.excepciones.DatosVaciosException;
import com.accesoControlClientes.excepciones.UsuarioNoEncontradoException;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.modelos.Usuario;
import com.accesoControlClientes.interfaces.UsuarioDAO;
import com.accesoControlClientes.servicios.UsuarioServicio;
import com.accesoControlClientes.util.ValidadorCampos;
import com.accesoControlClientes.util.ValidadorUsuario;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UsuarioImplement implements UsuarioServicio, UserDetailsService { //clase para cargar el password, username a spring security

    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;
    private final ValidadorUsuario validadorUsuario;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        log.info("[Listar Usuarios] Intentando listar usuarios");
        var usuarios = usuarioDAO.findAll();
        log.info("[Listar Usuarios] Usuarios encontrados: {}", usuarios.size());
        return usuarios;
    }

    @Override
    @Transactional
    public void guardar(Usuario usuarioNuevo) {
        log.info("[Guardar Usuario] Intentando guardar usuario: {}", usuarioNuevo.getUsername());

        if(validadorUsuario.esVacioUsuarioGuardar(usuarioNuevo)){
            log.warn("[Guardar Usuario] Usuario con datos vacíos");
            throw new DatosVaciosException();
        }

        validadorUsuario.limpiarUsuario(usuarioNuevo);

        var pass =usuarioNuevo.getPassword();
        if(!pass.startsWith("{bcrypt}")){
            pass= passwordEncoder.encode(pass);
            log.info("[Guardar Usuario] Password encriptado");
            usuarioNuevo.setPassword(pass);
        }

        usuarioDAO.save(usuarioNuevo);
        log.info("[Guardar Usuario] Usuario guardado con id: {}", usuarioNuevo.getId());
    }

    @Override
    @Transactional
    public void actualizar(Usuario usuarioActualizado) {
        log.info("[Actualizar Usuario] Intentando actualizar usuario con id: {}", usuarioActualizado.getId());

        if(validadorUsuario.esVacioUsuarioActualizar(usuarioActualizado)){
            log.warn("[Actualizar Usuario] Usuario con datos vacíos");
            throw new DatosVaciosException();
        }
        validadorUsuario.limpiarUsuario(usuarioActualizado);

        log.info("[Actualizar Usuario] Buscando usuario con id: {}", usuarioActualizado.getId());
        Usuario usuarioOrigen = buscar(usuarioActualizado.getId());
        usuarioOrigen.setUsername(usuarioActualizado.getUsername());

        if(!ValidadorCampos.esVacio(usuarioActualizado.getPassword())) {
            log.warn("[Actualizar Usuario] Codificando password");
            usuarioOrigen.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        usuarioDAO.save(usuarioOrigen);
        log.info("[Actualizar Usuario] Usuario con id {} actualizado", usuarioActualizado.getId());
    }

    @Override
    @Transactional
    public void eliminar(Long usuarioId) {
        log.info("[Eliminar Usuario] Intentando eliminar usuario con id: {}", usuarioId);

        if(ValidadorCampos.esVacio(usuarioId)){
            log.warn("[Eliminar Usuario] Dato vacio en usuarioId");
            throw new DatosVaciosException();
        }

        log.info("[Eliminar Usuario] Buscando usuario con id {}", usuarioId);
        var usuario = buscar(usuarioId);

        usuarioDAO.delete(usuario);
        log.info("[Eliminar Usuario] Usuario con id {} eliminado", usuarioId);
    }


    @Override
    @Transactional(readOnly = true)
    public Usuario buscar(Long usuarioId) {
        log.info("[Buscar Usuario] Intentando buscar usuario con id: {}", usuarioId);
        if(ValidadorCampos.esVacio(usuarioId)){
            log.warn("[Buscar Usuario] Dato vacio en usuarioId");
            throw new DatosVaciosException();
        }

        var usuario = usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException(usuarioId));

        log.info("[Buscar Usuario] Usuario con id {} encontrado", usuarioId);
        return usuario;

    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        log.info("[Buscar Usuario] Intentando buscar usuario con username: {}", username);
        if(ValidadorCampos.esVacio(username)){
            log.warn("[Buscar Usuario] Dato vacio en username");
            throw new DatosVaciosException();
        }
        var usernameN = username.trim();
        var usuario= usuarioDAO.findByUsername(usernameN)
                .orElseThrow( () -> new UsuarioNoEncontradoException(usernameN));

        log.info("[Buscar Usuario] Usuario con username {} encontrado", usernameN);
        return usuario;
    }

    @Override
    public boolean existePorUsername(String username) {
        log.info("[Existe Por Username] Intentando buscar usuario con username: {}", username);
        if(ValidadorCampos.esVacio(username)){
            log.warn("[Existe Por Username] Dato vacio en username");
            throw new DatosVaciosException();
        }
        username = username.trim();
        return usuarioDAO.findByUsername(username).isPresent();
    }

    //Usa UserDetailsService para obtener los detalles del usuario y pasarlos a Spring Security
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[Auth] Intentando autenticar usuario con username: {}", username);

        if(ValidadorCampos.esVacio(username)){
            log.warn("[Auth] Dato vacio en username");
            throw new BadCredentialsException("El username no puede estar vacío");
        }

        log.info("[Auth] Buscando usuario con username: {}", username);
        Usuario usuario = usuarioDAO.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario con username: " + username + " no fue encontrado"));
        usuario.getUsuarioRolSet().size(); //// Forzar carga de roles (lazy y no Earger)
        //Envio el usuario autenticado userDetails y recupera los roles en automático con getAuthorities
        log.info("[Auth] Usuario '{}' autenticado exitosamente", username);
        return new UsuarioAutenticado(usuario);


    }
}
