
package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
 @PostMapping("/registrar")
    public  registrar(string tipoUsuario )   {//tipo usuario va a ser un enum acordado con el front
       //recibe enum que diga si es profesional o paciente
       if (tipoUsuario == enumPaciente) {
           retrun "registrarpaciente.html"
       }else return "registrarprofesional.html"
         
     }
    @PostMapping ("/registrar/paciente")
    public registrarpaciente ()

@PostMapping ("/registrar/profesional")
    public registrarprofesional ()
            
}
