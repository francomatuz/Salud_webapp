package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.servicios.ImagenServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenControlador {

    private final ImagenServicio imagenServicio;

    public ImagenControlador (ImagenServicio imagenServicio) {
        this.imagenServicio = imagenServicio;
    }

    @PostMapping("/cargar")
    public ResponseEntity<String> cargarImagen(@RequestParam("archivo") MultipartFile archivo,
            @RequestParam("idPaciente") Long idPaciente,
            @RequestParam("idProfesional") Long idProfesional) {
        try {
            Imagen imagenGuardada = imagenServicio.guardarImagen(archivo, idPaciente, idProfesional);
            return ResponseEntity.ok("Imagen cargada exitosamente. ID: " + imagenGuardada.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al cargar la imagen: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable Long id) {
        try {
            // Lógica para recuperar la imagen por su ID
            byte[] imagenBytes = imagenServicio.obtenerImagenPorId(id);
            // Devuelve los bytes de la imagen en la respuesta
            return ResponseEntity.ok().body(imagenBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarImagen(@PathVariable Long id,
                                                   @RequestParam("archivo") MultipartFile archivo) {
        try {
            // Lógica para actualizar la imagen por su ID
            imagenServicio.actualizarImagen(id, archivo);
            return ResponseEntity.ok("Imagen actualizada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la imagen: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarImagen(@PathVariable Long id) {
        try {
            // Lógica para eliminar la imagen por su ID
            imagenServicio.eliminarImagen(id);
            return ResponseEntity.ok("Imagen eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la imagen: " + e.getMessage());
        }
    }
}