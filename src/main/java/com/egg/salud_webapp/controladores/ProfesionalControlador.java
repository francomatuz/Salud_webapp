package com.egg.salud_webapp.controladores;

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
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
public class ProfesionalControlador {
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/registrar/profesional")
    public String registrarProfesional(ModelMap modelo) {
        modelo.put("generos", GeneroEnum.values());
        modelo.put("especialidades", Especialidades.values());
        modelo.put("obrasSociales", ObraSocial.values());

        return "registrarprofesional.html";
    }

    @PostMapping("/registrar/profesional")
    public String registrarProfesional(@RequestParam String matricula, @RequestParam Especialidades especialidad,

            @RequestParam(value = "atencionVirtual", required = false) Boolean atencionVirtual,
            Double precio,
            @RequestParam(value = "prestadores", required = false) String[] prestadoresArray,
            @RequestParam MultipartFile archivo, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam GeneroEnum genero,
            ModelMap modelo) throws MiException {
        try {

            profesionalServicio.registrar(archivo, matricula, especialidad, atencionVirtual, precio, prestadoresArray,
                    nombre, apellido, dni, fecha_nac, email, password, password2, genero);

            modelo.put("exito", "Solicitud enviada exitosamente");

            return "login.html";
        } catch (MiException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("dni", dni);
            modelo.put("fecha de nacimiento", fecha_nac);
            modelo.put("matricula", matricula);
            modelo.put("generos", GeneroEnum.values());
            modelo.put("especialidades", Especialidades.values());
            modelo.put("obrasSociales", ObraSocial.values());

            return "registrarprofesional.html";
        }
    }

}