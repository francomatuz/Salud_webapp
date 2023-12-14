package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import com.egg.salud_webapp.servicios.TurnoServicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
@RequestMapping("/perfil2")
public class PerfilProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private TurnoServicio turnoServicio;

    @GetMapping("/actualizar")
    public String mostrarFormulario(ModelMap modelo, HttpSession session) {

        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (profesionalLogueado == null) {
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir
            // al inicio de sesión
            return "redirect:/login";
        }
        List<String> obrasSocialesSelected = new ArrayList<>();
        List<String> obrasSocialesList = new ArrayList<>();
        for (ObraSocial obraSocial : profesionalServicio
                .obtenerObrasSocialesPorIdProfesional(profesionalLogueado.getId())) {
            obrasSocialesSelected.add(obraSocial.toString());
        }
        for (ObraSocial obraSocial : ObraSocial.values()) {
            obrasSocialesList.add(obraSocial.toString());
        }

        modelo.put("profesional", profesionalLogueado);
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", obrasSocialesList);
        modelo.put("atencionVirtual", profesionalLogueado.getAtencionVirtual());
        modelo.put("prestadores", obrasSocialesSelected);

        return "actualizarprofesional.html";
    }

    @PostMapping("/actualizar")
    public String actualizarPerfil(@RequestParam MultipartFile archivo, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String dni, @RequestParam String matricula,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam String email,
            @RequestParam(value = "prestadores", required = false) List<ObraSocial> prestadores,
            @RequestParam GeneroEnum genero, Double precio, ModelMap modelo, HttpSession session)
            throws MiException {

        // Obtener el usuario logueado desde la sesión
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (profesionalLogueado == null) {

            return "redirect:/login";
        }

        try {
            // Actualizar el perfil del paciente (sin cambiar la contraseña)
            profesionalServicio.actualizar(profesionalLogueado, archivo, nombre, apellido, dni, matricula, fecha_nac,
                    email, prestadores,
                    genero, null, null, precio);

            Profesional profesionalActualizado = profesionalServicio.getById(profesionalLogueado.getId());
            // Cerrar sesión
            SecurityContextHolder.getContext().setAuthentication(null);

            modelo.put("profesional", profesionalActualizado);
            modelo.put("exito", "Perfil actualizado exitosamente");

            return "login.html"; // Se cierra sesión para poder actualizar los datos en el front

        } catch (MiException ex) {
            // Manejar excepciones
            Logger.getLogger(PerfilProfesionalControlador.class.getName()).log(Level.SEVERE, null,
                    ex);
            Profesional profesional = (Profesional) session.getAttribute("usuariosession");

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("generos", GeneroEnum.values());
            modelo.put("fecha de nacimiento", fecha_nac);
            modelo.put("email", email);
            modelo.put("matricula", matricula);
            modelo.put("especialidades", Especialidades.values());
            modelo.put("atencionVirtual", profesionalLogueado.getAtencionVirtual());

            List<String> obrasSocialesSelected = new ArrayList<>();
            List<String> obrasSocialesList = new ArrayList<>();
            for (ObraSocial obraSocial : profesionalServicio.obtenerObrasSocialesPorIdProfesional(profesional.getId())) {
                obrasSocialesSelected.add(obraSocial.toString());
            }
            for (ObraSocial obraSocial : ObraSocial.values()) {
                obrasSocialesList.add(obraSocial.toString());
            }
            modelo.put("obrasSociales", obrasSocialesList);
            modelo.put("prestadores", obrasSocialesSelected);
            modelo.put("profesional", profesionalLogueado);

            return "actualizarprofesional.html"; // Se queda en la misma página con cartel de error.
        }
    }

    // DAR DE BAJA
    @GetMapping("/darBaja")
    public String darBaja(HttpSession session, ModelMap modelo) throws MiException {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        profesionalServicio.darBaja(profesionalLogueado.getId());
        // logica para logout
        return "index.html";
    }

    // ELIMINAR
    @GetMapping("/eliminar")
    public String eliminar(HttpSession session, ModelMap modelo) throws MiException {

        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        try {

            profesionalServicio.eliminar(profesionalLogueado.getId());

            return "index.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:../dashboardprofesional.html";
        }

    }

    @GetMapping("/dashboard2")
     public String obtenerTurnosTomados(HttpSession session,ModelMap modelo) {
         Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        if (profesionalLogueado == null) {
            return "redirect:/login";
        }
         List<Turno> turnosTomados = turnoServicio.obtenerTurnosParaElProfesional(profesionalLogueado.getId());
         modelo.put("turnosTomados", turnosTomados);
        return "dashboardprofesional.html";
    }

    @GetMapping("/solicitarAlta")
    public String solicitarAlta(HttpSession session, ModelMap modelo) throws MiException {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        profesionalServicio.darAlta(profesionalLogueado.getId());
        SecurityContextHolder.getContext().setAuthentication(null);

        return "login.html";
    }

    @GetMapping("/generar-turnos")
    public String mostrarFormularioGenerarTurnos(ModelMap modelo, HttpSession session) {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        if (profesionalLogueado == null) {
            return "redirect:/login";
        }

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(1);
        int horarioInicio = 8; // Ejemplo: 8 AM
        int horarioFin = 17; // Ejemplo: 5 PM
        int duracionTurnoEnMinutos = 30; // Ejemplo: 30 minutos

        modelo.put("fechaInicio", fechaInicio);
        modelo.put("fechaFin", fechaFin);
        modelo.put("horarioInicio", horarioInicio);
        modelo.put("horarioFin", horarioFin);
        modelo.put("duracionTurnoEnMinutos", duracionTurnoEnMinutos);
        modelo.put("profesional", profesionalLogueado);

        return "generarTurnos.html";
    }

    @PostMapping("/generar-turnos")
    public String generarTurnosDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horarioInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horarioFin,
            @RequestParam int duracionTurnoEnMinutos,
            ModelMap modelo,
            HttpSession session) throws MiException {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        if (profesionalLogueado == null) {
            return "redirect:/login";
        }

        try {
            // Validaciones
            if (fechaFin.isBefore(fechaInicio)
                    || (fechaFin.isEqual(fechaInicio) && horarioInicio.isAfter(horarioFin))
                    || duracionTurnoEnMinutos <= 0) {
                modelo.put("Error", "Parámetros de entrada inválidos.");
                modelo.put("profesional", profesionalLogueado);
                return "error.html";
            }

            profesionalServicio.generarTurnosDisponibles(
                    profesionalLogueado.getId(),
                    fechaInicio,
                    fechaFin,
                    horarioInicio,
                    horarioFin,
                    duracionTurnoEnMinutos);

            modelo.put("Exito", "Turnos agregados correctamente");
            return "dashboardprofesional.html"; // Página de éxito

        } catch (MiException ex) {
            Logger.getLogger(PerfilProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("Error", ex.getMessage());
            modelo.put("profesional", profesionalLogueado);

            return "error.html"; // Página de error
        }
    }

    @PostMapping("/precio")
    public String settearPrecioConsulta(HttpSession session, @RequestParam Double precio, ModelMap modelo) throws MiException {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        try {
            List<Turno> turnosTomados = turnoServicio.obtenerTurnosParaElProfesional(profesionalLogueado.getId());
            modelo.put("turnosTomados", turnosTomados);
            profesionalServicio.settearPrecioConsulta(precio, profesionalLogueado.getId());
            return "redirect:/perfil2/dashboard2";
        } catch (MiException ex) {
            Logger.getLogger(PerfilProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("Error", ex.getMessage());
            modelo.put("profesional", profesionalLogueado);
            return "error.html";
        }
    }

    @GetMapping("/profesionales")
    public String TodosLosProfesioanlesActivos(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.put("profesionales", profesionales);
        return "todos-los-profesionales.html";
    }
            @PostMapping("/biografia")
    public String guardarBiografia(HttpSession session, String biografia, ModelMap modelo) throws MiException {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        if (profesionalLogueado == null) {
            return "redirect:/login";
        }
         List<Turno> turnosTomados = turnoServicio.obtenerTurnosParaElProfesional(profesionalLogueado.getId());
         modelo.put("turnosTomados", turnosTomados);
        profesionalServicio.guardarBio(profesionalLogueado.getId(), biografia);
        return "redirect:/perfil2/dashboard2"; 
        }
    
}
