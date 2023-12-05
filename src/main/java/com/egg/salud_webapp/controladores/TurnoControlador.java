package com.egg.salud_webapp.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.TurnoServicio;

@Controller
@RequestMapping("/turnos")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;

    @GetMapping("/disponibles")
    public String mostrarTurnosDisponibles(ModelMap modelo) {
        List<Turno> turnosDisponibles = turnoServicio.obtenerTurnosDisponibles();
        modelo.put("turnosDisponibles", turnosDisponibles);
        return "turnosDisponibles.html";
    }

    @PostMapping("/tomar")
    public String tomarTurno(@RequestParam Long idTurno,
            @RequestParam Long idPaciente,
            HttpSession session,
            ModelMap modelo) {
        try {
            turnoServicio.tomarTurno(idTurno, idPaciente);
            modelo.addAttribute("tomado", true); // Añadir parámetro de éxito
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar tomar el turno: " + ex.getMessage());
        }
        return "redirect:/turnos/disponibles";
    }


    @GetMapping("/tomados")
    public String mostrarTurnosTomados(@RequestParam Long idPaciente, ModelMap modelo) {
        try {
            List<Turno> turnosTomados = turnoServicio.obtenerTurnosTomadosPorPaciente(idPaciente);
            modelo.addAttribute("turnosTomados", turnosTomados);
            return "turnosTomados.html";
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar obtener los turnos tomados: " + ex.getMessage());
            return "error.html";
        }
    }
    @GetMapping("/mis-turnos")
    public String mostrarMisTurnos(ModelMap modelo, HttpSession session) {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        if (profesionalLogueado == null) {
            return "redirect:/login";
        }
        List<Turno> misTurnosDisponibles = turnoServicio.obtenerTurnosDisponiblesPorProfesional(profesionalLogueado.getId());
        modelo.put("misTurnosDisponibles", misTurnosDisponibles);
        modelo.put("profesional", profesionalLogueado);

        return "misTurnos.html";
    }
    @PostMapping("/eliminar-turno")
    public String eliminarTurno(@RequestParam Long idTurno, HttpSession session, ModelMap modelo) {
        try {
            turnoServicio.eliminarTurno(idTurno);
            modelo.addAttribute("Turno eliminado con exito", true); 
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar eliminar el turno: " + ex.getMessage());
        }
        return "redirect:/turnos/mis-turnos";
    }

    @PostMapping("/modificar-turno")
    public String modificarTurno(
            @RequestParam Long idTurno,
            @RequestParam(required = false) LocalDate nuevaFecha,
            @RequestParam(required = false) LocalTime nuevoHorario,
            ModelMap modelo
    ) {
        try {
            // No estoy seguro si deberías pasar también el id del paciente para la modificación.
            // En este ejemplo, lo he eliminado ya que el servicio no lo utiliza.
            // Si es necesario, puedes ajustar según tus requerimientos.
    
            turnoServicio.modificarTurno(idTurno, nuevaFecha, nuevoHorario);
    
            modelo.addAttribute("Turno modificado", true); // Añadir parámetro de éxito
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar modificar el turno: " + ex.getMessage());
        }
        return "redirect:/turnos/mis-turnos";
    }
    
}





