
package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.PacienteServicio;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/paciente") // localhost:8080/paciente
public class PacienteControlador {
    
    @Autowired
    private PacienteServicio pacienteServicio;
    
     @GetMapping("/registrar/paciente")
    public String registrarPaciente(ModelMap modelo) {
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", ObraSocial.values());
        return "registrarpaciente.html";
    }

    @PostMapping("/registrar/paciente")
    public String registrarpaciente(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) throws MiException {

        try {
            pacienteServicio.registrar(nombre, apellido, email, dni, fecha_nac, obraSocial, genero, password,
                    password2);

            modelo.put("Exito", "Paciente registrado exitosamente");

            return "login.html";

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

            return "error.html";

        }
    }

    @GetMapping("/actualizar/{id}")
    public String actualizarPaciente (@PathVariable Long id,ModelMap modelo) {
        
        modelo.put("paciente", pacienteServicio.getById(id));
      
        return "actualizarpaciente.html";
    }
    
    @PostMapping("/actualizar/{id}")
       public String actualizarPaciente(@PathVariable Long id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) {

        try {
            pacienteServicio.actualizar(id, nombre, apellido, email, dni, fecha_nac, obraSocial, genero, password, password2);
            modelo.put("exito", "usuario modificado correctamente");
            
            return "dashboardpaciente.html";
            
        } catch (MiException ex) {
            modelo.put("error",ex.getMessage());  
        }
        
        return "actualizarpaciente.html";
}
       
       
       
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable Long id) {
        Paciente paciente = pacienteServicio.getById(id);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }      
}
