package com.accesoControlClientes.controlador;

import com.accesoControlClientes.DTO.UsuarioEditarDTO;
import com.accesoControlClientes.DTO.mapper.UsuarioMapper;
import com.accesoControlClientes.controlador.helper.UsuarioVistaHelper;
import com.accesoControlClientes.excepciones.AppException;
import com.accesoControlClientes.security.UsuarioAutenticado;
import com.accesoControlClientes.servicios.UsuarioServicio;
import com.accesoControlClientes.security.SecurityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@Slf4j
public class ControladorCuenta {

    private final UsuarioServicio usuarioServicio;
    private final SecurityService securityService;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioVistaHelper usuarioVistaHelper;

    @GetMapping("/cuenta")
    public String cuenta(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                         Model model){
        log.info("[Get/Cuenta] Recuperando datos de la cuenta");
        var usuario = usuarioMapper.toDto(usuarioAutenticado.getUsuario());
        model.addAttribute("usuario", usuario);
        return "cuenta";
    }

    @PostMapping("/cuenta")
    public String actualizar(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
                             @Valid UsuarioEditarDTO dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){

        log.info("[Post/Cuenta] Intentando actualizar datos de la cuenta");

        if(result.hasErrors()){
            log.warn("[POST /cuenta] Errores de validación para usuario ");
            return "cuenta";
        }
        try{
            if(usuarioVistaHelper.esUsuarioActual(usuarioAutenticado, dto.getId())){
                usuarioServicio.actualizar(usuarioMapper.toEditarEntity(dto));
                log.info("[Post/Cuenta] Actualizando datos de la cuenta");
            }else{
                log.warn("[Post/Cuenta] Intento no autorizado de actualizar");
                throw new AppException("No se puede actualizar el usuario");
            }

            //Refrescar la sesión con nuevo username
            securityService.refreshSession(dto.getUsername());
            log.info("[Post/Cuenta] Datos de la sesion actualizados");
            redirectAttributes.addFlashAttribute("success", "Cuenta actualizada con exito");
        }catch (AppException e){
            log.error("[POST /cuenta] Error durante la actualización");
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cuenta";
    }

}
