/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.repositorios.FichaMedicaRepositorio;
import com.egg.salud_webapp.repositorios.HistoriaClinicaRepositorio;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FichaMedicaServicios implements UserDetailsService {

  @Autowired
  private FichaMedicaRepositorio fichaMedicaRepositorio;

  @Autowired
  private HistoriaClinicaRepositorio historiaClinicaRepositorio;

  public void crear(
    LocalDate fecha,
    String diagnostico,
    String tratamiento,
    String notas,
    Long id
  ) {
    // validar atributos
    FichaMedica fichaMedica = new FichaMedica();

    fichaMedica.setFecha(fecha);
    fichaMedica.setDiagnostico(diagnostico);
    fichaMedica.setTratamiento(tratamiento);
    fichaMedica.setNotas(notas);

    HistoriaClinica historiaClinica = historiaClinicaRepositorio.buscarPorId(
      2L
    ); // (idHistoriaClinica)

    if (historiaClinica != null) {
      fichaMedica.setHistoriaClinica(historiaClinica);
      fichaMedicaRepositorio.save(fichaMedica);
      List<FichaMedica> fichasMedicas = historiaClinica.getFichasMedicas();
      fichasMedicas.add(fichaMedica);
      historiaClinica.setFichasMedicas(fichasMedicas);
      historiaClinicaRepositorio.save(historiaClinica);
    } else {
      throw new RuntimeException("No se encontró la historia clínica con ID: ");
    }
  }

  // public void RecuperarFichaMedica() throws Exception{
  // HistoriaClinica historiaClinica = historiaClinicaRepositorio.buscarPorId(2L);
  //
  // FichaMedica fichaMedica = fichaMedicaRepositorio.buscarPorId(1L);
  //
  //
  // if(fichaMedica!=null){
  // System.out.println(fichaMedica);
  // }else{
  // throw new Exception("No se encuentra la ficha medica");
  // }
  //
  // }
  public void RecuperarFichaMedica() throws Exception {
    // Recuperar la historia clínica con ID 2
    HistoriaClinica historiaClinica = historiaClinicaRepositorio.buscarPorId(
      2L
    );

    // Verificar si la historia clínica existe
    if (historiaClinica != null) {
      // Recuperar la colección de fichas médicas dentro de la historia clínica
      List<FichaMedica> fichasMedicas = historiaClinica.getFichasMedicas();

      // Verificar si hay fichas médicas en la colección
      if (!fichasMedicas.isEmpty()) {
        // Puedes iterar sobre la colección o seleccionar la ficha médica que necesitas
        for (FichaMedica fichaMedica : fichasMedicas) {
          if (fichaMedica.getId() == 2L) { // Cambia esto según tu lógica de búsqueda
            System.out.println(fichaMedica);
            return;
          }
        }
        // Si llegas aquí, significa que no se encontró la ficha médica con el ID 1
        throw new Exception("No se encuentra la ficha médica con ID 1");
      } else {
        throw new Exception(
          "No hay fichas médicas asociadas a la historia clínica"
        );
      }
    } else {
      throw new Exception("No se encuentra la historia clínica");
    }
  }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
