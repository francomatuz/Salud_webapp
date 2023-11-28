package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imagen")
public class ImagenControlador {
    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/noticia/{id}")
    public ResponseEntity<byte[]>Imagen(@PathVariable long id){
        Paciente paciente = pacienteServicio.getOne(id);
        byte[] imagen = paciente.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
    }
}