package com.accesoControlClientes.servicios.implement;

import com.accesoControlClientes.dominio.Persona;
import com.accesoControlClientes.excepciones.DatosVaciosException;
import com.accesoControlClientes.excepciones.PersonaNoEncontradaException;
import com.accesoControlClientes.interfaces.PersonaDAO;
import com.accesoControlClientes.servicios.PersonaServicio;
import com.accesoControlClientes.util.ValidadorCampos;
import com.accesoControlClientes.util.ValidadorPersona;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class PersonaImplement implements PersonaServicio {

    private PersonaDAO personaDAO;
    private ValidadorPersona validadorPersona;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> listarPersonas() {
        log.info("Intentando listar personas");
        List<Persona> personas = personaDAO.findAll();
        log.info("Personas encontradas: {}", personas.size());
        return personas;
    }

    @Override
    @Transactional
    public void guardar(Persona persona) {
        log.info("Intentando guardar persona: {}", persona);
        if(validadorPersona.esVacioPersona(persona)){
            log.warn("Datos vacios en persona: {}", persona);
            throw new DatosVaciosException();
        }
        validadorPersona.normalizarPersona(persona);
        personaDAO.save(persona);
        log.info("Persona guardada con id: {}", persona.getId());
    }

    @Override
    @Transactional
    public void eliminar(Long personaId) {
        log.info("Intentando eliminar persona con id: {}", personaId);
        if(ValidadorCampos.esVacio(personaId)){
            log.warn("Dato vacio en personaId en eliminar");
            throw new DatosVaciosException();
        }
        var persona =personaDAO.findById(personaId)
                .orElseThrow(()-> new PersonaNoEncontradaException(personaId));
        personaDAO.delete(persona);
        log.info("Persona con id {} eliminada correctamente", personaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona buscar(Long personaId) {
        log.info("Intentando buscar persona con id: {}", personaId);
        if(ValidadorCampos.esVacio(personaId)){
            log.warn("Dato vacio en personaId en buscar");
            throw new DatosVaciosException();
        }
        var persona = personaDAO.findById(personaId).orElseThrow(()-> new PersonaNoEncontradaException(personaId));
        log.info("Persona con id {} encontrada", persona.getId());
        return persona;
    }
}
