package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.egg.salud_webapp.servicios.ProfesionalServicio;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/perfil2")
public class PerfilProfesionalControlador {

@Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/actualizar")
    public String mostrarFormulario(ModelMap modelo, HttpSession session) {
        
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (profesionalLogueado == null) {
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir al inicio de sesión
            return "redirect:/login";
        }

        
        modelo.put("paciente", profesionalLogueado);
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", ObraSocial.values());

        return "actualizarprofesional.html"; // Nombre del formulario de actualización de perfil
    }


    @PostMapping("/actualizar")
    public String actualizarPerfil(@RequestParam String nombre, @RequestParam String apellido,@RequestParam String dni,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam String email, 
            
            @RequestParam List <ObraSocial> prestadores, @RequestParam GeneroEnum genero, ModelMap modelo, HttpSession session)
            throws MiException {

        // Obtener el usuario logueado desde la sesión
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (profesionalLogueado == null) {
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir al inicio de sesión
            return "redirect:/login";
        }

        try {
            // Actualizar el perfil del paciente (sin cambiar la contraseña)
            profesionalServicio.actualizar(profesionalLogueado.getId(), nombre, apellido, dni, fecha_nac, email, prestadores,
                    genero, null , null);

            modelo.put("Exito", "Perfil actualizado exitosamente");

            return "dashboardprofesional.html"; // Página de perfil actualizado

        } catch (MiException ex) {
            // Manejar excepciones
            Logger.getLogger(PerfilPacienteControlador.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("Error", ex.getMessage());
            modelo.put("paciente", profesionalLogueado);

            return "error.html"; // Página de error
        }
    }
}