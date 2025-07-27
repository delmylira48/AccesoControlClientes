package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.PersonaGuardarDTO;
import com.accesoControlClientes.DTO.mapper.PersonaMapper;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.servicios.PersonaServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorPersona {

    private final PersonaServicio personaServicio;
    private final PersonaMapper personaMapper;

    @GetMapping("/")
    public String inicio(Model model){
        log.info("[GET/Inicio] Listando personas");
        var personas = personaServicio.listarPersonas();
        log.info("[GET/Inicio] Personas encontradas: {}", personas.size());
        model.addAttribute("personas", personaMapper.toDtoList(personas));
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model){
        log.info("[GET/Agregar] Presentando interfaz de agregar persona");
        model.addAttribute("persona", new PersonaGuardarDTO());
        return "modificar";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes){
        log.info("[POST/Editar] Presentando interfaz de editar persona");
        try{
            log.info("[POST/Editar] Buscando persona con id: {}", id);
            var persona = personaServicio.buscar(id);
            log.info("[POST/Editar] Persona encontrada con id: {}", persona.getId());
            model.addAttribute("persona", persona);
        }catch (AppException e){
            log.error("[POST/Editar] Error al buscar la persona: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid  @ModelAttribute("persona") PersonaGuardarDTO dto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes){
        log.info("[POST/Guardar] Intentando guardar persona");
        if (result.hasErrors()){
            log.warn("[POST/Guardar] Error validando el formulario");
            model.addAttribute("persona", dto);
            return "modificar";
        }

        try{
            log.info("[POST/Guardar] Guardando persona");
            var persona = personaMapper.toGuardarEntity(dto);
            personaServicio.guardar(persona);
            log.info("[POST/Guardar] Persona guardada con exito. Id: {}", persona.getId());
            redirectAttributes.addFlashAttribute("success", "Persona guardada con exito");
        }catch (AppException e){
            log.error("[POST/Guardar] Error al guardar la persona: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Long personaId, RedirectAttributes redirectAttributes){
        log.info("[POST/Eliminar] Intentando eliminar persona con id: {}", personaId);
        try{
            log.info("[POST/Eliminar] Eliminando persona con id: {}", personaId);
            personaServicio.eliminar(personaId);
            redirectAttributes.addFlashAttribute("success", "Persona eliminada con exito");
        }catch (AppException e){
            log.error("[POST/Eliminar] Error al eliminar la persona: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }
}
