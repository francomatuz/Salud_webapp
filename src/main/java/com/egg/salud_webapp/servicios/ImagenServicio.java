package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.repositorios.ImagenRepositorio;
import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ImagenServicio {
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MiException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());

                imagen.setName(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage()); // err: el mensaje va a ser de color rojo
            }
        }
        return null;
    }

    @Transactional
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
                imagen.setMime(archivo.getContentType());
                imagen.setName(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    public void eliminarImagen(String id) {
        // Lógica para eliminar la imagen por su ID
        imagenRepositorio.deleteById(id);
    }
}
