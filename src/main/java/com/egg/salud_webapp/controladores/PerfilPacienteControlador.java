package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.egg.salud_webapp.servicios.PacienteServicio;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import com.egg.salud_webapp.servicios.TurnoServicio;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/perfil")
public class PerfilPacienteControlador {

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private TurnoServicio turnoServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, ModelMap modelo) {
        Paciente pacienteLogueado = (Paciente) session.getAttribute("usuariosession");
        try {
            List<Turno> misTurnos = turnoServicio.obtenerTurnosTomadosPorPaciente(pacienteLogueado.getId());
            List<Turno> misTurnosFinalizados = pacienteServicio.obtenerTurnosFinalizados(pacienteLogueado.getId());
            modelo.addAttribute("misTurnosFinalizados", misTurnosFinalizados);
            modelo.addAttribute("misTurnos", misTurnos);
            return "dashboardpaciente_1.html";
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar obtener los turnos tomados: " + ex.getMessage());
            return "error.html";
        }
    }

    // ELIMINAR
    @GetMapping("/eliminar")
    public String eliminar(HttpSession session, ModelMap modelo) throws MiException {

        Paciente pacienteLogueado = (Paciente) session.getAttribute("usuariosession");

        try {

            pacienteServicio.eliminar(pacienteLogueado.getId());

            return "index.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:../dashboardpaciente.html";
        }

    }

    @GetMapping("/actualizar")
    public String mostrarFormulario(ModelMap modelo, HttpSession session) {

        Paciente pacienteLogueado = (Paciente) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (pacienteLogueado == null) {
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir
            // al inicio de sesión
            return "redirect:/login";
        }

        modelo.put("paciente", pacienteLogueado);
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", ObraSocial.values());

        return "actualizarpaciente.html"; // Nombre del formulario de actualización de perfil
    }

    @PostMapping("/actualizar")
    public String actualizarPerfil(@RequestParam MultipartFile archivo, @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email, @RequestParam String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero, ModelMap modelo, HttpSession session)
            throws MiException {

        // Obtener el usuario logueado desde la sesión
        Paciente pacienteLogueado = (Paciente) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (pacienteLogueado == null) {

            return "redirect:/login";
        }

        try {
            // Actualizar el perfil del paciente (sin cambiar la contraseña)
            pacienteServicio.actualizar(pacienteLogueado, archivo, nombre, apellido, email, dni, fecha_nac, obraSocial,
                    genero, null, null);

            Paciente pacienteActualizado = pacienteServicio.getById(pacienteLogueado.getId());
            // Cerrar sesión
            SecurityContextHolder.getContext().setAuthentication(null);

            modelo.put("paciente", pacienteActualizado);
            modelo.put("exito", "Perfil actualizado exitosamente");

            return "login.html"; // Se cierra sesión para poder actualizar los datos en el front

        } catch (MiException ex) {
            // Manejar excepciones
            Logger.getLogger(PerfilPacienteControlador.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("generos", GeneroEnum.values());
            modelo.put("fecha de nacimiento", fecha_nac);
            modelo.put("email", email);
            modelo.put("obrasSociales", ObraSocial.values());
            modelo.put("paciente", pacienteLogueado);

            return "actualizarpaciente.html";
        }
    }

    @PostMapping("/calificar")
    public String calificarTurno(@RequestParam Long idTurno, @RequestParam Long idProfesional,
            @RequestParam Integer calif, ModelMap modelo) {
        try {
            turnoServicio.calificar(idTurno);
            System.out.println(idProfesional);
            profesionalServicio.calificacionProfesional(idProfesional, calif);
            modelo.addAttribute("Turno calificado con éxito", true);
            return "redirect:/perfil/dashboard";
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar calificar el turno: " + ex.getMessage());
            return "error.html";
        }
    }

    @GetMapping("/profesionales")
    public String TodosLosProfesionalesActivos(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.put("profesionales", profesionales);
        return "todos-los-profesionales.html";
    }
}
