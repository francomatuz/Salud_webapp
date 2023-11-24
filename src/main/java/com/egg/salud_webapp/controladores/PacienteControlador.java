
package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.servicios.ImagenServicio;
import com.egg.salud_webapp.servicios.PacienteServicio;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/paciente") // localhost:8080/paciente
public class PacienteControlador {
    
    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable Long id) {
        Paciente paciente = pacienteServicio.getById(id);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }   
   @PostMapping("/{pacienteId}/imagen")
    public ResponseEntity<String> cargarImagenPaciente(@PathVariable Long pacienteId, @RequestParam("file") MultipartFile file) {
        try {
            byte[] imageData = file.getBytes();
            Imagen imagen = imagenServicio.cargarImagen(imageData);

            // Asignar la imagen al paciente
            Paciente paciente = pacienteServicio.obtenerPaciente(pacienteId);
            paciente.setImagen(imagen);
            pacienteServicio.guardarPaciente(paciente);

            return new ResponseEntity<>("Imagen cargada con éxito", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al cargar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 
    
    
    
