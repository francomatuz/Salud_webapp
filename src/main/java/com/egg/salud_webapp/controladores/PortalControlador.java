package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.enumeraciones.Rol;
import com.egg.salud_webapp.excepciones.MiException;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar(Rol rol) {//tipo usuario va a ser un enum acordado con el front
        //recibe enum que diga si es profesional o paciente
        if (rol == Rol.PACIENTE) {
            return "registrarpaciente.html";
        } else if (rol == Rol.PROFESIONAL) {

            return "registrarprofesional.html";
        } else{
            // Ver la posibilidad de otro caso o lanzar una excepcion si es necesario
            throw new MiException ("Rol no valido"+rol);
                    
        }

        
        //falta logica
        @PostMapping("/registrar/paciente")
        public String registrarpaciente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String dni, @RequestParam LocalDate fecha_nac, @RequestParam ObraSocial obraSocial, @RequestParam GeneroEnum genero @RequestParam String password, @RequestParam String password2){
        
             try { 
                 pacienteServicio.
            
        } catch (Exception e) {
        }
        
        return "login.html";
        }
        
        
        
        

        @PostMapping("/registrar/profesional")
        public String registrarprofesional(){
        
        return "solicitud.html";
        }

    }
}
