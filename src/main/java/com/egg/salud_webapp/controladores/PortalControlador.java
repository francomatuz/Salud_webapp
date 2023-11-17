package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.PacienteServicio;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/")
    public String index() {
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
    public String registrarpaciente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String dni, @RequestParam(name = "fecha_nac") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac, @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero, @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws MiException {
       
        
        try {
            pacienteServicio.registrar(nombre, apellido, email, dni, fecha_nac, obraSocial, genero, password, password2);

            modelo.put("Exito", "Paciente registrado exitosamente");

            return "index.html";

        } catch (MiException ex) {

            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("Error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("dni", dni);
            modelo.put("fecha de nacimiento", fecha_nac);
            modelo.put("obra social", obraSocial);
            modelo.put("genero", genero);

            return "login.html";

        }
    }
    
        @GetMapping("/login")
        public String login(@RequestParam(required = false) String error, ModelMap modelo){
        
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
        }
        
        return "login.html";
    } 
        @PostMapping("/logincheck")
public String loginCheck(@RequestParam String email, @RequestParam String password, ModelMap modelo) {
    // Aquí deberías realizar la lógica de autenticación, por ejemplo, usando Spring Security.
    // Puedes usar el servicio de Spring Security o tu propio servicio para verificar las credenciales.

    // Ejemplo simple (debes adaptarlo a tus necesidades):
    if (email.equals("usuario@example.com") && password.equals("contraseña")) {
        // Autenticación exitosa, puedes redirigir a la página de inicio u otra página deseada.
        return "redirect:/inicio";
    } else {
        // Autenticación fallida, agrega un mensaje de error y redirige a la página de login.
        modelo.put("error", "Usuario o contraseña incorrectos");
        return "login.html";
    }
}

    }


