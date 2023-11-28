package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.salud_webapp.servicios.PacienteServicio;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboardpaciente.html";
    }
    //ELIMINAR
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
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir al inicio de sesión
            return "redirect:/login";
        }

        
        modelo.put("paciente", pacienteLogueado);
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", ObraSocial.values());

        return "actualizarpaciente.html"; // Nombre del formulario de actualización de perfil
    }

    @PostMapping("/actualizar")
    public String actualizarPerfil(@RequestParam MultipartFile archivo,@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero, ModelMap modelo, HttpSession session)
            throws MiException {

        // Obtener el usuario logueado desde la sesión
        Paciente pacienteLogueado = (Paciente) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (pacienteLogueado == null) {
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir al inicio de sesión
            return "redirect:/login";
        }

        try {
            // Actualizar el perfil del paciente (sin cambiar la contraseña)
            pacienteServicio.actualizar(archivo,pacienteLogueado.getId(), nombre, apellido, email, dni, fecha_nac, obraSocial,
                    genero, null, null);

            modelo.put("Exito", "Perfil actualizado exitosamente");

            return "index.html"; // Página de perfil actualizado CAMBIAR A DASHBOARD

        } catch (MiException ex) {
            // Manejar excepciones
            Logger.getLogger(PerfilPacienteControlador.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("Error", ex.getMessage());
            modelo.put("paciente", pacienteLogueado);

            return "error.html"; // Página de error
        }
    }
    //a ver si anda
}