package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.PacienteServicio;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

   @GetMapping("/")
    public String index(ModelMap modelo) {
         List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }

    @GetMapping("/registrar/paciente")
    public String registrarPaciente(ModelMap modelo) {
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", ObraSocial.values());
        return "registrarpaciente.html";
    }

    @PostMapping("/registrar/paciente")
    public String registrarpaciente(@RequestParam String nombre, @RequestParam MultipartFile archivo,
            @RequestParam String apellido,
            @RequestParam String email, @RequestParam String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) throws MiException, IOException {

        try {
            pacienteServicio.registrar(archivo, nombre, apellido, email, dni, fecha_nac, obraSocial, genero, password,
                    password2);

            modelo.put("exito", "Paciente registrado exitosamente");

            return "login.html";

        } catch (MiException ex) {

            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("dni", dni);
            modelo.put("fecha de nacimiento", fecha_nac);
            modelo.put("generos", GeneroEnum.values());
            modelo.put("obrasSociales", ObraSocial.values());

            return "registrarpaciente.html";

        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
        }

        return "login.html";
    }
       
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MOD')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Object usuarioSession = session.getAttribute("usuariosession");

        if (usuarioSession instanceof Paciente) {
            Paciente logueado = (Paciente) usuarioSession;
            if (logueado.getRol().toString().equals("ADMIN")) {
                return "redirect:/";
            }
        }

        return "redirect:/";
    }
      
}
