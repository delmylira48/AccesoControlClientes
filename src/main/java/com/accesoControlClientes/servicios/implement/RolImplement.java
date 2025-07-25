package com.accesoControlClientes.servicios.implement;

import com.accesoControlClientes.excepciones.DatosVaciosException;
import com.accesoControlClientes.excepciones.RolNoEncontradoException;
import com.accesoControlClientes.excepciones.RolNombreYaExisteException;
import com.accesoControlClientes.modelos.Rol;
import com.accesoControlClientes.interfaces.RolDAO;
import com.accesoControlClientes.servicios.RolServicio;
import com.accesoControlClientes.util.ValidadorCampos;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
class RolImplement implements RolServicio {

    private final RolDAO rolDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarRoles() {
        log.info("[listarRoles] Intentando listar roles");
        var roles = rolDAO.findAll();
        log.info("[listarRoles] Roles encontrados: {}", roles.size());
        return roles;
    }

    @Override
    @Transactional
    public void guardar(Rol rol) {
        log.info("[guardar roles] Intentando guardar rol: {}", rol.getNombre());
        if(ValidadorCampos.esVacio(rol.getNombre())) {
            log.warn("[guardar roles] Datos vacios en rol");
            throw new DatosVaciosException();
        }
        var nombre = rol.getNombre().trim();
        rol.setNombre(nombre);
        if(rolDAO.findByNombre(nombre).isPresent()){
            throw new RolNombreYaExisteException(nombre);
        }
        rolDAO.save(rol);
        log.info("[guardar roles] Rol guardado con id: {}", rol.getId());
    }

    @Override
    @Transactional
    public void eliminar(Long rolId) {
        log.info("[Eliminar roles] Intentando eliminar rol con id: {}", rolId);
        if(ValidadorCampos.esVacio(rolId)) {
            log.warn("[Eliminar roles] Dato vacio en rolId en eliminar");
            throw new DatosVaciosException();
        }

        log.info("[Eliminar roles] Buscando rol con id: {}", rolId);
        var rol = rolDAO.findById(rolId).orElseThrow(() -> {
            log.warn("[Eliminar roles] Rol no encontrado con id: {}", rolId);
            return new RolNoEncontradoException(rolId);
        });

        rolDAO.delete(rol);
        log.info("[Eliminar roles] Rol con id {} eliminado correctamente", rolId);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol buscar(Long rolId) {
        log.info("[Buscar rol] Intentando buscar rol con id: {}", rolId);

        if(ValidadorCampos.esVacio(rolId)) {
            throw new DatosVaciosException();
        }

        var rol = rolDAO.findById(rolId).orElseThrow(() -> {
            log.warn("[Buscar rol] Rol no encontrado con id: {}", rolId);
            return new RolNoEncontradoException(rolId);
        });

        log.info("[Buscar rol] Rol encontrado con id: {}", rolId);
        return rol;
    }

    @Override
    @Transactional(readOnly = true)
    public Rol buscarPorNombre(String nombre) {
        log.info("[Buscar rol por nombre] Intentando buscar rol con nombre: {}", nombre);

        if(ValidadorCampos.esVacio(nombre)) {
            log.warn("[Buscar rol por nombre] Dato vacio en nombre en buscar");
            throw new DatosVaciosException();
        }

        var rol = rolDAO.findByNombre(nombre.trim()).orElseThrow(()-> {
            log.warn("[Buscar rol por nombre] Rol no encontrado con nombre: {}", nombre);
            return new RolNoEncontradoException(nombre);
        });

        log.info("[Buscar rol por nombre] Rol encontrado con nombre: {}", nombre);
        return rol;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> obtenerNombresRoles() {
        log.info("[obtenerNombresRoles] Intentando obtener nombres de roles");
        var roles = rolDAO.findAll().stream().map(rol -> rol.getNombre()).toList();
        log.info("[obtenerNombresRoles] Roles encontrados: {}", roles.size());
        return roles;
    }
}
