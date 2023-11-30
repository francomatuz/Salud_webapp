package com.egg.salud_webapp.servicios;
import com.egg.salud_webapp.repositorios.ImagenRepositorio;

import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.excepciones.MiException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
@Transactional
@Autow
public class ImagenServicio {

     private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen cargar(MultipartFile archivo)throws MiException{
        if (archivo!=null) {
            try{
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setName(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                imagen = imagenRepositorio.save(imagen); // Guardar la imagen en el repositorio
                return imagen;
            }catch(IOException ex){
                System.out.println(ex.getMessage());  
            }
        }
        return null;
    }


    @Transactional
    public Imagen actualizar(MultipartFile archivo, String idImagen)throws MiException{
        if (archivo !=null) {
            try{
                Imagen imagen = new Imagen();
                if (idImagen!=null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen=respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setName(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            }catch(IOException ex){
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
