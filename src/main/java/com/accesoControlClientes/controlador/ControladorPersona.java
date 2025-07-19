package com.accesoControlClientes.controlador;

import com.accesoControlClientes.dominio.Persona;
import com.accesoControlClientes.servicios.PersonaServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorPersona {

    private final PersonaServicio personaServicio;

    @GetMapping("/thymeleaf")
    public String inicio(Model model, @AuthenticationPrincipal User user){ //imprime el usuario logueado con AuthenticationPrincipal
        var personas = personaServicio.listarPersonas();
        log.info("Ejecutando controlador MVC");
        model.addAttribute("personas", personas);
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model){
        model.addAttribute("persona", new Persona());
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, Errors errores){
        if (errores.hasErrors()){
            return "modificar";
        }

        personaServicio.guardar(persona);
        return "redirect:/thymeleaf";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam("id") Long id, Model model){
        var persona = personaServicio.buscar(id);
        model.addAttribute("persona", persona);
        return "modificar";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Long personaId){
        personaServicio.eliminar(personaId);
        return "redirect:/thymeleaf";
    }
}
