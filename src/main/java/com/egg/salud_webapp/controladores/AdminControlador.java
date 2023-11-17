
package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.servicios.PacienteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
        @Autowired
    private PacienteServicio pacienteServicio;
    
   @GetMapping("/dashboard")
   public String panelAdministrativo(){
       return "panel.html";
   }
   
   @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("Pacientes", pacientes);

        return "paciente_list";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable Long id){
        pacienteServicio.cambiarRol(id);
        
       return "redirect:/admin/usuarios";
    }
   
}


