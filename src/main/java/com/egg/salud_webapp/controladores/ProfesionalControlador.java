package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Profesional;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/registrar")
    public String registrarProfesional(ModelMap modelo) {
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", ObraSocial.values());
        modelo.put("especialidades",Especialidades.values());
        
        return "registrarprofesional.html";
    }

    @PostMapping("/registrar")
    public String registrarProfesional(@RequestParam String matricula, @RequestParam Especialidades especialidad,

            @RequestParam String direccion,
            @RequestParam Boolean atencionVirtual, @RequestParam String bio,
            @RequestParam List<ObraSocial> prestadores, @RequestParam Long id, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String dni,
            @RequestParam LocalDate fecha_nac, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam GeneroEnum genero, @RequestParam UsuarioEnum rol,
            @RequestParam Boolean alta, ModelMap modelo) throws MiException {
        try {
            profesionalServicio.registrar(matricula, especialidad,
                    direccion, atencionVirtual, bio, prestadores, id, nombre, apellido, dni, fecha_nac,
                    email, password, password2, genero, alta);

            modelo.put("Exito", "Solicitud enviada exitosamente");

            return "index.html";
        } catch (MiException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("Error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("dni", dni);
            modelo.put("fecha de nacimiento", fecha_nac);
            modelo.put("genero", genero);
            modelo.put("matricula", matricula);
            modelo.put("bio", bio);

            return "error.html";
        }
    }
    //despues vemos si le damos segun filtros, este es para todos
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        return "profesional_lista.html";
    }
    
    @GetMapping("/lista")
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) throws MiException {
      
        modelo.put("profesional", profesionalServicio.getById(id));
        
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obraSociales", ObraSocial.values());
        modelo.put("especialidad",Especialidades.values());
        
        
        return "profesional_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(Long id, String nombre, String apellido, String dni, LocalDate fecha_nac, String email,
            String password, String password2, List<ObraSocial> prestadores,
            String direccion, Boolean atencionVirtual, String bio, ModelMap modelo) throws MiException{
        try {
            modelo.put("generos", GeneroEnum.values());
            modelo.put("obraSociales", ObraSocial.values());
            modelo.put("especialidad",Especialidades.values());
            
            

            profesionalServicio.actualizar(id, nombre, apellido, dni, fecha_nac, email, password, password2, prestadores, direccion, atencionVirtual, bio);
            
                        
            return "redirect:../";

        } catch (MiException ex) {
            
            
            modelo.put("error", ex.getMessage());
            
            return "profesional_actualizar.html";
        }
    }
}
