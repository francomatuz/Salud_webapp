package com.egg.salud_webapp;


import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;

import com.egg.salud_webapp.servicios.ProfesionalServicio;
import java.time.LocalDate;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
    public class SaludWebappApplication implements CommandLineRunner {
        
    @Autowired
    private ProfesionalServicio profesionalServicio;
    
    public static void main(String[] args) {
        SpringApplication.run(SaludWebappApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        registrar();
        //  leerPaciente(10L);
        // eliminarPaciente(4L);
        // modificarPaciente(10L);
    }
     
    
    public void registrar(){
        try{
           profesionalServicio.registrar("MAT001", Especialidades.CARDIOLOGIA, "Dirección1", true,
                "Bio1", new ObraSocial[]{ObraSocial.GALENO, ObraSocial.OMINT}, null, "Nombre1", "Apellido1", "123456789",
                LocalDate.of(1980, 1, 1), "email1@example.com", "password1", "password1", GeneroEnum.MASCULINO, true);
        }catch(MiException e){
            System.out.println("Error al intentar eliminar al paciente: " + e.getMessage());
        }
    }
    
    
    
    public void leerPaciente(Long idProfesional) throws MiException {
        
        Profesional profesional = profesionalServicio.getById(idProfesional);
        
        if (profesional != null) {
        System.out.println("Datos del profesional:");
        System.out.println("ID: " + profesional.getId());
        System.out.println("Nombre: " + profesional.getNombre());
        System.out.println("Apellido: " + profesional.getApellido());
        System.out.println("Email: " + profesional.getEmail());
        System.out.println("Dni: " + profesional.getDni());
        System.out.println("Fecha de Naciemiento: " + profesional.getFecha_nac());
        System.out.println("Especialidad: " + profesional.getEspecialidad());
        System.out.println("Genero: " + profesional.getGenero());
        } else {
            System.out.println("No se encontró ningún paciente con el ID proporcionado.");
        }
    }
    
    public void eliminarProfesional(Long idProfesional) {
        try {
            profesionalServicio.eliminar(idProfesional);
            System.out.println("Paciente eliminado exitosamente.");
        } catch (MiException e) {
            System.out.println("Error al intentar eliminar al paciente: " + e.getMessage());
        }
    }
    
    public void modificarProfesional(Long idProfesional) {
        
        try {
            profesionalServicio.actualizar(1L, "Juan", "Pérez", "123456789", LocalDate.of(1985, 7, 15),
        "juan.perez@example.com", "password123", "password123",
        new ObraSocial[]{ObraSocial.GALENO, ObraSocial.SANCOR_SALUD},
        "Calle Principal 123", true, "Médico con experiencia en atención virtual y presencial.");
        } catch (MiException ex) {
            Logger.getLogger(SaludWebappApplication.class.getName()).log(Level.SEVERE, "ERROR", ex);
        }
    }
    
}
