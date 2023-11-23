package com.egg.salud_webapp.controladores;

import java.time.LocalDate;
import java.util.List;
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

@Controller
@RequestMapping
public class ProfesionalControlador {
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/registrar/profesional")
    public String registrarProfesional(ModelMap modelo) {
        modelo.put("generos", GeneroEnum.values());

        return "registrarprofesional.html";
    }

    @PostMapping("/registrar/profesional")
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
}
