package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
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

 @GetMapping("/disponibles")  // FALTA AGREGAR QUE SI NO SELECCIONA ESPECIALIDAD DEBE SELECCIONARLA
    public String mostrarTurnosDisponibles(
        @RequestParam(name = "especialidad", required = false) String especialidad,
        @RequestParam(name = "atencionVirtual", defaultValue = "false") boolean atencionVirtual,
        ModelMap modelo
    ) {
        List<Turno> turnosDisponibles;
    
        if (especialidad != null) {
            turnosDisponibles = atencionVirtual ?
                turnoServicio.obtenerTurnosDisponiblesYAtencionVirtual(especialidad) :
                turnoServicio.obtenerTurnosDisponiblesPorEspecialidad(especialidad);
        } else {
            turnosDisponibles = atencionVirtual ?
                turnoServicio.obtenerTurnosDisponiblesYAtencionVirtual(especialidad) :
                turnoServicio.obtenerTurnosDisponibles();
        }
    
        List<String> especialidades = turnoServicio.obtenerEspecialidadesDisponibles();
        modelo.put("especialidades", especialidades);
    
        // Agregar la lista filtrada al modelo
        modelo.put("turnosDisponibles", turnosDisponibles);
    
        return "turnosDisponibles.html";
    }

    @GetMapping("/tomados")
    public String obtenerTurnosTomados(ModelMap modelo) {
        List<Turno> turnosTomados = turnoServicio.obtenerTurnosTomados();
        modelo.put("turnosTomados", turnosTomados);
        return "turnosTomados.html";
    }

    @PostMapping("/tomar")
    public String tomarTurno(@RequestParam Long idTurno,
            HttpSession session,
            ModelMap modelo) {
        Paciente pacienteLogueado = (Paciente) session.getAttribute("usuariosession");
        try {
            turnoServicio.tomarTurno(idTurno, pacienteLogueado.getId());
            modelo.addAttribute("tomado", true); // Añadir parámetro de éxito
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar tomar el turno: " + ex.getMessage());
        }
        return "redirect:/turnos/disponibles";
    }

    @GetMapping("/mis-turnos-paciente")
    public String mostrarTurnosDePaciente(ModelMap modelo, HttpSession session) {
        Paciente pacienteLogueado = (Paciente) session.getAttribute("usuariosession");
        try {
            List<Turno> misTurnos = turnoServicio.obtenerTurnosTomadosPorPaciente(pacienteLogueado.getId());
            modelo.addAttribute("misTurnos", misTurnos);
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
        List<Turno> misTurnosDisponibles = turnoServicio
                .obtenerTurnosDisponiblesPorProfesional(profesionalLogueado.getId());
        modelo.put("misTurnosDisponibles", misTurnosDisponibles);
        modelo.put("profesional", profesionalLogueado);
        

        return "turnos-profesional.html";
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

    @PostMapping("/marcar-finalizado")
    public String marcarTurnoComoFinalizado(@RequestParam Long idTurno, ModelMap modelo) {
        try {
            turnoServicio.marcarTurnoComoFinalizado(idTurno);
            modelo.addAttribute("Turno marcado como finalizado", true);
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar marcar el turno como finalizado: " + ex.getMessage());
        }

        return "redirect:/turnos/profesional-turnos";
    }

    @PostMapping("/modificar-turno")
    public String modificarTurno(
            @RequestParam Long idTurno,
            @RequestParam(required = false) LocalDate nuevaFecha,
            @RequestParam(required = false) LocalTime nuevoHorario,
            ModelMap modelo) {
        try {
            // No estoy seguro si deberías pasar también el id del paciente para la
            // modificación.
            // En este ejemplo, lo he eliminado ya que el servicio no lo utiliza.
            // Si es necesario, puedes ajustar según tus requerimientos.

            turnoServicio.modificarTurno(idTurno, nuevaFecha, nuevoHorario);

            modelo.addAttribute("Turno modificado", true); // Añadir parámetro de éxito
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar modificar el turno: " + ex.getMessage());
        }
        return "redirect:/turnos/mis-turnos";
    }

    @PostMapping("/cancelar-turno")
    public String cancelarTurno(@RequestParam Long idTurno, ModelMap modelo) {
        try {
            turnoServicio.cancelarTurno(idTurno);
            modelo.addAttribute("Turno cancelado con éxito", true);
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar cancelar el turno: " + ex.getMessage());
        }
        return "redirect:/perfil/dashboard";
    }

    @PostMapping("/cancelar-profesional")
    public String cancelarTurnoProfesional(@RequestParam Long idTurno, ModelMap modelo) {
        try {
            turnoServicio.cancelarTurno(idTurno);
            modelo.addAttribute("Turno cancelado con éxito", true);
        } catch (MiException ex) {
            modelo.addAttribute("error", "Error al intentar cancelar el turno: " + ex.getMessage());
        }
        return "redirect:/perfil2/dashboard2";
    }

}
