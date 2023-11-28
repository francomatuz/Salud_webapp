package com.egg.salud_webapp;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.servicios.FichaMedicaServicios;
import com.egg.salud_webapp.servicios.PacienteServicio;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ComponentScan(basePackages = "com.egg.salud_webapp")
public class SaludWebappApplication implements CommandLineRunner {

  // @Autowired
  // private PacienteServicio pacienteServicio;

  @Autowired
  private FichaMedicaServicios fichaMedicaServicios;

  public static void main(String[] args) {
    SpringApplication.run(SaludWebappApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // crearFichaMedica();
   mostrarFichaMedica();
    // leerPaciente(12L);
    // eliminarPaciente(4L);
    // modificarPaciente(10L);

	
  }

  // public void leerPaciente(Long idPaciente) {
  //
  // Paciente paciente = pacienteServicio.getById(idPaciente);
  //
  // if (paciente != null) {
  // System.out.println("Datos del paciente:");
  // System.out.println("ID: " + paciente.getId());
  // System.out.println("Nombre: " + paciente.getNombre());
  // System.out.println("Apellido: " + paciente.getApellido());
  // System.out.println("Email: " + paciente.getEmail());
  // System.out.println("Dni: " + paciente.getDni());
  // System.out.println("Fecha de Naciemiento: " + paciente.getFecha_nac());
  // System.out.println("Obra Social: " + paciente.getObraSocial());
  // System.out.println("Genero: " + paciente.getGenero());
  // } else {
  // System.out.println("No se encontró ningún paciente con el ID
  // proporcionado.");
  // }
  // }
  //
  //
  // public void eliminarPaciente(Long idPaciente) {
  // try {
  // pacienteServicio.eliminar(idPaciente);
  // System.out.println("Paciente eliminado exitosamente.");
  // } catch (MiException e) {
  // System.out.println("Error al intentar eliminar al paciente: " +
  // e.getMessage());
  // }
  // }
  //
  //
  // public void modificarPaciente(Long idPaciente) {
  //
  // try {
  // pacienteServicio.actualizar(idPaciente, "Marcos", "Fama", null, null, null,
  // null, null, null, null);
  // } catch (MiException ex) {
  // Logger.getLogger(SaludWebappApplication.class.getName()).log(Level.SEVERE,
  // "ERROR", ex);
  // }
  // }

  public void crearFichaMedica() {
    LocalDate fechaTurno = LocalDate.of(2023, 11, 10);

    fichaMedicaServicios.crear(
        fechaTurno,
        "Migranias",
        "Migral1gr",
        "1 capsula cuando aparece el dolor de cabeza",
        2L);
  }

  public void mostrarFichaMedica() throws Exception {
    fichaMedicaServicios.RecuperarFichaMedica();
  }
}
