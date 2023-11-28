package com.egg.salud_webapp;

import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaludWebappApplication {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    public static void main(String[] args) {
        SpringApplication.run(SaludWebappApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        listarProfesionalesSinAprobar();
//        registrar();
//        leerPaciente(1L);
//        eliminarProfesional(1L);
//        modificarProfesional(1L);
//    }
//    public void registrar() {
//
//        try {
//            List<ObraSocial> obrasSociales = Arrays.asList(ObraSocial.GALENO, ObraSocial.OMINT);
//            obrasSociales.toString();
//            LocalDate fechaNacimiento = LocalDate.of(1980, 1, 1);
//
//            profesionalServicio.registrar("12345", Especialidades.CARDIOLOGIA, true,
//                    new String[]{"ObraSocial1", "ObraSocial2"},
//                    "Nombre", "Apellido", "12345678",
//                    LocalDate.of(1990, 1, 1), "correo@ejemplo.com",
//                    "contraseña", "contraseña", GeneroEnum.MASCULINO);
//        } catch (MiException e) {
//            System.out.println("Error al intentar registrar al profesional: " + e.getMessage());
//        }
//    }
//
//}
//    public void leerPaciente(Long idProfesional) throws MiException {
//
//        Profesional profesional = profesionalServicio.getById(idProfesional);
//
//        if (profesional != null) {
//            System.out.println("Datos del profesional:");
//            System.out.println("ID: " + profesional.getId());
//            System.out.println("Nombre: " + profesional.getNombre());
//            System.out.println("Apellido: " + profesional.getApellido());
//            System.out.println("Email: " + profesional.getEmail());
//            System.out.println("Dni: " + profesional.getDni());
//            System.out.println("Fecha de Naciemiento: " + profesional.getFecha_nac());
//            System.out.println("Especialidad: " + profesional.getEspecialidad());
//            System.out.println("Genero: " + profesional.getGenero());
//        } else {
//            System.out.println("No se encontró ningún paciente con el ID proporcionado.");
//        }
//    }
//    
//    public void eliminarProfesional(Long idProfesional) {
//        try {
//            profesionalServicio.eliminar(idProfesional);
//            System.out.println("Paciente eliminado exitosamente.");
//        } catch (MiException e) {
//            System.out.println("Error al intentar eliminar al paciente: " + e.getMessage());
//        }
//    }
//    public void modificarProfesional(Long idProfesional) {
//        List<ObraSocial> obrasSociales = Arrays.asList(ObraSocial.OSDE, ObraSocial.SANCOR_SALUD);
//        try {
//            profesionalServicio.actualizar(1L, "María", "López", "123456789", LocalDate.of(1990, 8, 20),
//                    "maria.lopez@example.com", "nuevaClave", "nuevaClave", obrasSociales,
//                    "Calle Secundaria 789", true, "Especialista en cardiología con enfoque en salud preventiva.");
//        } catch (MiException ex) {
//            Logger.getLogger(SaludWebappApplication.class.getName()).log(Level.SEVERE, "ERROR", ex);
//        }
//    }
//    public void listarProfesionalesSinAprobar() {
//        List<Profesional> profeSinAprobar = profesionalServicio.listaProfesionalesSinAprobar();
//        for (Profesional profesional : profeSinAprobar) {
//            System.out.println(profesional);
//
//        }
//    }
}
