package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.enumeraciones.Rol;
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
    public String index() {
        return "index.html";
    }

    @PostMapping("/registrar")
    public String registrar(Rol rol) {//tipo usuario va a ser un enum acordado con el front
        //recibe enum que diga si es profesional o paciente
        if (rol == Rol.PACIENTE) {
            return "registrarpaciente.html";
        } else if (rol == Rol.PROFESIONAL) {

            return "registrarprofesional.html";
        } else{
            // Ver la posibilidad de otro caso o lanzar una excepcion si es necesario
            return "error.html";
        }

        
        
        
        //falta logica
        @PostMapping("/registrar/paciente")
        public String registrarpaciente(){
        
        
        return "login.html";
        }

        @PostMapping("/registrar/profesional")
        public String registrarprofesional(){
        
        return "solicitud.html";
        }

    }
}
