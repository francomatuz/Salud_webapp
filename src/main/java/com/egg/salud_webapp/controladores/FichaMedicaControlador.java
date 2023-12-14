package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.FichaMedicaServicio;
import com.egg.salud_webapp.servicios.PacienteServicio;
import com.egg.salud_webapp.servicios.TurnoServicio;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/fichamedica")
public class FichaMedicaControlador {

  @Autowired
  private FichaMedicaServicio fichaMedicaServicio;

  @Autowired
  private TurnoServicio turnoServicio;

  @Autowired
  private PacienteServicio pacienteServicio;

  @GetMapping("/registrar")
  public String registrar() {
    return "historiaclinica.html";
  }

  @GetMapping("/turnos-paciente/{idPaciente}")
  public ResponseEntity<List<Turno>> obtenerTurnosPorPaciente(@PathVariable Long idPaciente) {
    try {
      List<Turno> turnos = turnoServicio.obtenerTurnosTomadosPorPaciente(idPaciente);
      return new ResponseEntity<>(turnos, HttpStatus.OK);
    } catch (MiException e) {
      // Manejar la excepción según tus necesidades
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/paciente/{id}/{idTurno}")
  public String mostrarPaciente(@PathVariable Long id, @PathVariable  Long idTurno, ModelMap modelo) throws MiException {
    Paciente paciente = pacienteServicio.getById(id);
    turnoServicio.marcarTurnoComoFinalizado(idTurno);

    if (paciente != null) {
      byte[] imagenBytes = paciente.getImagen().getContenido();
      String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);

      modelo.addAttribute("paciente", paciente);
      modelo.addAttribute("imagenBase64", imagenBase64);
      // modelo.addAttribute("fechaNacimientoFormateada",
      // paciente.getFecha_nac().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        HistoriaClinica historiaClinica = fichaMedicaServicio.BuscarByPaciente(paciente.getId());
        System.out.println(paciente.getId());
        System.out.println(historiaClinica.getId());
        List<FichaMedica> fichasMedicas = fichaMedicaServicio.listarFichasMedicasPorPaciente(historiaClinica.getId());
        modelo.put("fichasMedicas", fichasMedicas);
      
      
      return "historiaclinica.html"; // El nombre de la plantilla Thymeleaf
    } else {
      return "error404"; // Puedes crear una plantilla específica para errores si lo deseas
    }
  }
  
  
  
  
    @PostMapping("/crear/{id}")
    public String crearFichaMedica(@PathVariable Long id, @RequestParam Long idProfesional, @RequestParam String diagnostico,
            @RequestParam String tratamiento, @RequestParam String notas, ModelMap modelo, HttpSession session)
            throws MiException {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        if (profesionalLogueado == null) {

            return "redirect:/login";
        }

        fichaMedicaServicio.crearFichaMedica(id, idProfesional,diagnostico, tratamiento, notas);

        if (profesionalLogueado == null) {
            return "redirect:/login";
        }
        List<Turno> turnosTomados = turnoServicio.obtenerTurnosParaElProfesional(profesionalLogueado.getId());
        modelo.put("turnosTomados", turnosTomados);
        modelo.put("Exito", "Ficha Medica creada");
        return "redirect:/perfil2/dashboard2";
    }

}
