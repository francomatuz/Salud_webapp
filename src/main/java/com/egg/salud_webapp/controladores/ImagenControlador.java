package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.servicios.PacienteServicio;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/imagen")
public class ImagenControlador {
    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/paciente/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Paciente paciente = pacienteServicio.getOne(id);

        if (paciente == null || paciente.getImagen() == null || paciente.getImagen().getContenido() == null) {
            // Manejo si el paciente no tiene imagen o si la imagen es nula
            return ResponseEntity.notFound().build();
        }

        byte[] imagenBytes = paciente.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);
    }
}