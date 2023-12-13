package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.repositorios.ImagenRepositorio;
import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.Tipo;
import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

@Service
public class ImagenServicio {
    @Autowired
     private ImagenRepositorio imagenRepositorio;
    
    @Transactional
    public Imagen guardar(MultipartFile archivo, Tipo tipoUsuario) throws MiException {
        Imagen imagen = new Imagen();
        try { 
            if (archivo == null || archivo.isEmpty()) {
                imagen = obtenerImagen(tipoUsuario);
            }else{ 
                imagen.setMime(archivo.getContentType());
                imagen.setName(StringUtils.cleanPath(archivo.getOriginalFilename()));
                imagen.setContenido(archivo.getBytes());

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return imagenRepositorio.save(imagen);
    }
    
    @Transactional
    public Imagen obtenerImagen(Tipo tipoUsuario) throws MiException {
        String nombreArchivo = null;
        byte[] contenido = null;

        if ("profesional".equalsIgnoreCase(tipoUsuario.toString())) {
            nombreArchivo = "doctor.png";
        } else {
            nombreArchivo = "paciente.png";
        }

        // Usa ClassPathResource para cargar la imagen desde la carpeta donde esta almacenada
        ClassPathResource resource = new ClassPathResource("static/img/" + nombreArchivo);
        try (InputStream inputStream = resource.getInputStream()) {
            //Sacarle el contenido a la imagen guardada
            contenido = FileCopyUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            // Manejar la excepción según tus necesidades
            throw new RuntimeException("Error al leer el contenido del archivo", e);
        }
        Imagen imagenPredeterminada = new Imagen();
        imagenPredeterminada.setMime("image/png"); // Ajusta el tipo MIME según el formato de tus imágenes
        imagenPredeterminada.setName(nombreArchivo);
        imagenPredeterminada.setContenido(contenido);
        return imagenPredeterminada;
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
