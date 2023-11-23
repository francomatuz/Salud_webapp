
package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagenes")

public class ImagenControlador {
  @Autowired
    private ImagenServicio imagenServicio;

    @PostMapping("/cargar")
    public ResponseEntity<String> cargarImagen(@RequestParam("file") MultipartFile file) {
        // Lógica para guardar la imagen en la base de datos y/o sistema de archivos
        // Puedes usar imagenServicio para gestionar la lógica del servicio
        Long imagenId = imagenServicio.guardarImagen(file);
        return ResponseEntity.ok("Imagen cargada con éxito. ID de imagen: " + imagenId);
    }
}  
    
    
    
    
    
    

