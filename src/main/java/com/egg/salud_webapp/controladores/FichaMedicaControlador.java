package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.FichaMedicaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fichamedica")
public class FichaMedicaControlador {

  @Autowired
  private FichaMedicaServicio fichaMedicaServicio;

  @GetMapping("/registrar")
  public String registrar() {
    return "registrarficha.html";
  }

  @PostMapping("/crear")
  public String crearFichaMedica(
      @RequestParam String diagnostico,
      @RequestParam String tratamiento,
      @RequestParam String notas,
      @RequestParam ModelMap modelo) throws MiException {
    fichaMedicaServicio.crearFichaMedica(
        diagnostico,
        tratamiento,
        notas);
    modelo.put("Exito", "Ficha Medica creada");
    return "dashboardprofesional.html";
  }
}
