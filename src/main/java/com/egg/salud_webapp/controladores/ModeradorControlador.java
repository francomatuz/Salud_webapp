
package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.SolicitudEnum;
import com.egg.salud_webapp.repositorios.ProfesionalRepositorio;
import com.egg.salud_webapp.servicios.PacienteServicio;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderador")
public class ModeradorControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "dashboardmoderador.html";
    }

    @GetMapping("/solicitudes")
    public String listarProfesionalesSolicitudes(ModelMap modelo) {
        List<Profesional> profesionalesSolicitud = profesionalServicio.listarProfesionalesSolicitud();
        modelo.addAttribute("Profesionales", profesionalesSolicitud);
        return "solicitudes.html";
    }
    
    @GetMapping("/modificarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        profesionalServicio.cambiarEstado(id);
        return "redirect:/moderador/profesionales";
    }
    
    @GetMapping("/aprobarSolicitud/{id}")
    public String aprobarSolicitud(@PathVariable Long id, ModelMap modelo) {
        try {

            Profesional profesional = profesionalRepositorio.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Profesional no encontrado"));

            profesional.setAlta(SolicitudEnum.ACTIVO);

            profesionalRepositorio.save(profesional);
            modelo.put("exito!", "la solicitud ha sido aprobada correctamente.");

        } catch (RuntimeException ex) {
            Logger.getLogger(ModeradorControlador.class.getName()).log(Level.SEVERE, "Error al aprobar la solicitud.", ex);

            modelo.put("error!", "error al aprobar la solicitud.");

        }
        return "redirect:/moderador/solicitudes";
    }
}
