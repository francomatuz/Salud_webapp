package com.egg.salud_webapp;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.PacienteServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.egg.salud_webapp")
public class SaludWebappApplication implements CommandLineRunner  {

    @Autowired
    private PacienteServicio pacienteServicio;

    public static void main(String[] args) {
        SpringApplication.run(SaludWebappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        
        leerPaciente(10L);
        //eliminarPaciente(4L);
       // modificarPaciente(10L);
    }

    public void leerPaciente(Long idPaciente) {
        
        Paciente paciente = pacienteServicio.getOne(idPaciente);

        if (paciente != null) {
            System.out.println("Datos del paciente:");
            System.out.println("ID: " + paciente.getId());
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Apellido: " + paciente.getApellido());
            System.out.println("Email: " + paciente.getEmail());
            System.out.println("Dni: " + paciente.getDni());
            System.out.println("Fecha de Naciemiento: " + paciente.getFecha_nac());
            System.out.println("Obra Social: " + paciente.getObraSocial());
            System.out.println("Genero: " + paciente.getGenero());
        } else {
            System.out.println("No se encontró ningún paciente con el ID proporcionado.");
        }
    }

    public void eliminarPaciente(Long idPaciente) {
        try {
            pacienteServicio.eliminar(idPaciente);
            System.out.println("Paciente eliminado exitosamente.");
        } catch (MiException e) {
            System.out.println("Error al intentar eliminar al paciente: " + e.getMessage());
        }
    }
     public void modificarPaciente(Long idPaciente) {
        
        try {
            pacienteServicio.actualizar(idPaciente, "Marcos", null, null, null, null, null, null, null, null);
        } catch (MiException ex) {
            Logger.getLogger(SaludWebappApplication.class.getName()).log(Level.SEVERE, "ERROR", ex);
        }
    }
        
     
    
    
}
