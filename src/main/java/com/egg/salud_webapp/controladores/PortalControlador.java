package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.Rol;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.PacienteServicio;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String registrar(Rol rol) throws MiException {        if (null == rol) {
        // Ver la posibilidad de otro caso o lanzar una excepcion si es necesario
        throw new MiException ("Rol no valido"+rol);
        
    } else //tipo usuario va a ser un enum acordado con el front
     //recibe enum que diga si es profesional o paciente
     switch (rol) {
         case PACIENTE:
             return "registrarpaciente.html";
         case PROFESIONAL:
             return "registrarprofesional.html";
         default:
             // Ver la posibilidad de otro caso o lanzar una excepcion si es necesario
             throw new MiException ("Rol no valido"+rol);
     }

    }
        //falta logica
        @PostMapping("/registrar/paciente")
        public String registrarpaciente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String dni, @RequestParam LocalDate fecha_nac, @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero, @RequestParam String password, @RequestParam String password2,ModelMap modelo) throws MiException{
        
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
             
               return "registrarpaciente.html";
             
        }
        
        }
    

    }

