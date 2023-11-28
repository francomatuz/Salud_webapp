package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
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
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("Pacientes", pacientes);

        return "paciente_list";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable Long id) {
        pacienteServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/profesionalesSinAprobar")
    public String listarProfesionalesSinAprobar(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listaProfesionalesSinAprobar();
        modelo.addAttribute("Profesionales", profesionales);
        return "profesionalesSinAprobar_list";
    }

    @GetMapping("/profesionales")
    public String listarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("Profesionales", profesionales);
        return "profesionales_list";
    }

    @GetMapping("/profesionalesAprobados")
    public String listarProfesionalesAprobados(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listaProfesionalesAprobados();
        modelo.addAttribute("Profesionales", profesionales);
        return "profesionalesAprobados_list";
    }

    @GetMapping("/aprobarSolicitud/{id}")
    public String aprobarSolicitud(@PathVariable Long id, ModelMap modelo) {
        try {

            Profesional profesional = profesionalRepositorio.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Profesional no encontrado"));

            profesional.setAlta(true);
            profesionalRepositorio.save(profesional);
            modelo.put("Exito!", "La solicitud ha sido aprobada correctamente.");

        } catch (RuntimeException ex) {
            Logger.getLogger(AdminControlador.class.getName()).log(Level.SEVERE, "Error al aprobar la solicitud.", ex);

            modelo.put("Error!", "Error al aprobar la solicitud.");

        }
        return "redirect:/admin/solicitudes";
    }
}
