package com.egg.salud_webapp;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SaludWebappApplication implements CommandLineRunner {

    @Autowired
    private PacienteServicio pacienteServicio;

    public static void main(String[] args) {
        SpringApplication.run(SaludWebappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Ejemplo de consulta por ID
        Long idPaciente = 7L; // Reemplaza con el ID que desees consultar
        Paciente paciente = pacienteServicio.getOne(idPaciente);

        // Imprimir los datos del paciente (o hacer lo que desees con la información)
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
            // Agrega más líneas según los campos que quieras mostrar
        } else {
            System.out.println("No se encontró ningún paciente con el ID proporcionado.");
        }
    }
}

