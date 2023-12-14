package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.SolicitudEnum;
import com.egg.salud_webapp.excepciones.MiException;
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
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "dashboardadmin.html";
    }

    @GetMapping("/pacientes")
    public String listar(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("Pacientes", pacientes);
        return "listapacientes.html";
    }

    @GetMapping("/solicitudes")
    public String listarProfesionalesSolicitudes(ModelMap modelo) {
        List<Profesional> profesionalesSolicitud = profesionalServicio.listarProfesionalesSolicitud();
        modelo.addAttribute("Profesionales", profesionalesSolicitud);
        return "solicitudes.html";
    }

    @GetMapping("/profesionales")
    public String listarProfesionalesSinSolicitudes(ModelMap modelo) {
        List<Profesional> profesionalesSinSolicitud = profesionalServicio.listarProfesionalesSinSolicitud();
        modelo.addAttribute("Profesionales", profesionalesSinSolicitud);
        return "listaprofesionales.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable Long id) {
        profesionalServicio.cambiarRol(id);
        return "redirect:/admin/profesionales";
    }

    @GetMapping("/modificarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        profesionalServicio.cambiarEstado(id);
        return "redirect:/admin/profesionales";
    }

    @GetMapping("/aprobarSolicitud/{id}")
    public String aprobarSolicitud(@PathVariable Long id, ModelMap modelo) throws MiException {
        try {

            Profesional profesional = profesionalRepositorio.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Profesional no encontrado"));

            profesionalServicio.darAlta(id);
//            profesional.setAlta(SolicitudEnum.ACTIVO);

            profesionalRepositorio.save(profesional);
            modelo.put("exito!", "La solicitud ha sido aprobada correctamente.");

        } catch (RuntimeException ex) {
            Logger.getLogger(AdminControlador.class.getName()).log(Level.SEVERE, "Error al aprobar la solicitud.", ex);

            modelo.put("error!", "Error al aprobar la solicitud.");

        }
        return "redirect:/admin/solicitudes";
    }

    @GetMapping("/cancelarSolicitud/{id}")
    public String cancelarSolicitud(@PathVariable Long id, ModelMap modelo) {
        try {

            Profesional profesional = profesionalRepositorio.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Profesional no encontrado"));

            profesional.setAlta(SolicitudEnum.INACTIVO);

            profesionalRepositorio.save(profesional);
            modelo.put("Exito!", "La solicitud ha sido rechazada correctamente.");

        } catch (RuntimeException ex) {
            Logger.getLogger(AdminControlador.class.getName()).log(Level.SEVERE, "Error al aprobar la solicitud.", ex);

            modelo.put("Error!", "Error al aprobar la solicitud.");

        }
        return "redirect:/admin/solicitudes";
    }

}
