package com.egg.salud_webapp.servicios;
import com.egg.salud_webapp.repositorios.ImagenRepositorio;

import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImagenServicio {

     private final ImagenRepositorio imagenRepositorio;
    private final PacienteServicio pacienteServicio;
    private final ProfesionalServicio profesionalServicio;

    // Constructor para inyección de dependencias
    public ImagenServicio(ImagenRepositorio imagenRepositorio, PacienteServicio pacienteServicio, ProfesionalServicio profesionalServicio) {
        this.imagenRepositorio = imagenRepositorio;
        this.pacienteServicio = pacienteServicio;
        this.profesionalServicio = profesionalServicio;
    }

    public Imagen guardarImagen(MultipartFile archivo, Long idPaciente, Long idProfesional) throws IOException {
        // Lógica para guardar la imagen y asociarla a un paciente y un profesional
        Imagen imagen = new Imagen();
        imagen.setData(archivo.getBytes());  // Guarda los bytes de la imagen

        // Asocia la imagen al paciente y al profesional utilizando sus ID
        Paciente paciente = pacienteServicio.obtenerPacientePorId(idPaciente);
        Profesional profesional = profesionalServicio.obtenerProfesionalPorId(idProfesional);

        imagen.setPaciente(paciente);
        imagen.setProfesional(profesional);

        return imagenRepositorio.save(imagen);
    }
    public Imagen cargarImagen(byte[] imageData) {
        Imagen imagen = new Imagen();
        imagen.setData(imageData);
        return imagenRepositorio.save(imagen);
    }

    public byte[] obtenerImagenPorId(Long id) {
        // Lógica para obtener la imagen por su ID
        // Utiliza el repositorio para acceder a la base de datos
        Optional<Imagen> optionalImagen = imagenRepositorio.findById(id);
        return optionalImagen.map(Imagen::getData).orElse(null);

    }

    public void actualizarImagen(Long id, MultipartFile archivo) throws IOException {
        // Lógica para actualizar la imagen por su ID
        Imagen imagen = imagenRepositorio.findById(id).orElse(null);
        if (imagen != null) {
            imagen.setData(archivo.getBytes());
            imagenRepositorio.save(imagen);
        }
    }

    public void eliminarImagen(Long id) {
        // Lógica para eliminar la imagen por su ID
        imagenRepositorio.deleteById(id);
    }
}
