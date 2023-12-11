
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.repositorios.FichaMedicaRepositorio;
import com.egg.salud_webapp.repositorios.HistoriaClinicaRepositorio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FichaMedicaServicio implements UserDetailsService {

  @Autowired
  private FichaMedicaRepositorio fichaMedicaRepositorio;

  @Autowired
  private HistoriaClinicaRepositorio historiaClinicaRepositorio;

  public void crearFichaMedica(
      String diagnostico, String tratamiento, String notas) {
    // validar atributos
    FichaMedica fichaMedica = new FichaMedica();

    fichaMedica.setFecha(LocalDate.now());
    fichaMedica.setDiagnostico(diagnostico);
    fichaMedica.setTratamiento(tratamiento);
    fichaMedica.setNotas(notas);

    HistoriaClinica historiaClinica = historiaClinicaRepositorio.buscarPorId(
        2L); // (idHistoriaClinica)

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

  public void RecuperarFichaMedica() throws Exception {
    // Recuperar la historia clínica con ID 2
    HistoriaClinica historiaClinica = historiaClinicaRepositorio.buscarPorId(
        2L);

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
            "No hay fichas médicas asociadas a la historia clínica");
      }
    } else {
      throw new Exception("No se encuentra la historia clínica");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                   // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
}
