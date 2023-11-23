package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.ImagenRepositorio;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardar(MultipartFile archivo) throws MiException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage()); //err: el mensaje va a ser de color rojo
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                // Ejemplo de validación de tipo de archivo
                if (!archivo.getContentType().startsWith("image/")) {
                    throw new MiException("El archivo no es una imagen");
                }

// Generar un nombre único para la imagen
                String nombreUnico = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
                imagen.setNombre(nombreUnico);

                if (idImagen == null) {
    throw new MiException("ID de imagen no puede ser nulo");
}

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }

    public Long guardarImagen(MultipartFile file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
