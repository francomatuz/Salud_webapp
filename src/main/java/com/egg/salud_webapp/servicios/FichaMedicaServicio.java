package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.repositorios.FichaMedicaRepositorio;
import com.egg.salud_webapp.repositorios.HistoriaClinicaRepositorio;
import com.egg.salud_webapp.repositorios.PacienteRepositorio;
import com.egg.salud_webapp.repositorios.TurnoRepositorio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

  @Autowired
  private PacienteRepositorio pacienteRepositorio;

  @Autowired
  private TurnoRepositorio turnoRepositorio;
  @Autowired
  private ProfesionalServicio profesionalServicio;

  public void crearFichaMedica(Long Id, Long idProfesional, String diagnostico, String tratamiento, String notas) {
    // validar atributos
    FichaMedica fichaMedica = new FichaMedica();

    fichaMedica.setFecha(LocalDate.now());
    fichaMedica.setDiagnostico(diagnostico);
    fichaMedica.setTratamiento(tratamiento);
    fichaMedica.setNotas(notas);

    // Recuperar la historia clínica asociada al paciente
    Optional<Paciente> pacienteOptional = pacienteRepositorio.findById(Id);
    Profesional profesional = profesionalServicio.getById(idProfesional);

    if (pacienteOptional.isPresent()) {
      Paciente paciente = pacienteOptional.get();

      HistoriaClinica historiaClinica = paciente.getHistoriaClinica();
      fichaMedica.setProfesional(profesional);

      if (historiaClinica != null) {
        // fichaMedica.setHistoriaClinica(historiaClinica);
        // fichaMedicaRepositorio.save(fichaMedica);
        // List<FichaMedica> fichasMedicas = historiaClinica.getFichasMedicas();
        historiaClinica.getFichasMedicas().add(fichaMedica); // agrego esta linea para reemplazar llineas 54 y 55 y 52
        fichaMedica.setHistoriaClinica(historiaClinica);
        fichaMedicaRepositorio.save(fichaMedica);
        // fichasMedicas.add(fichaMedica);
        // historiaClinica.setFichasMedicas(fichasMedicas);
        historiaClinicaRepositorio.save(historiaClinica);
      } else {
        throw new RuntimeException("No se encontró la historia clínica ");
      }
    } else {
      throw new RuntimeException("No se encontró el paciente  ");
    }
  }

  public FichaMedica buscarFichaMedicaPorId(Long id) {
    return fichaMedicaRepositorio.buscarPorId(id);
  }

  public List<FichaMedica> listarTodasLasFichasMedicas() {
    return fichaMedicaRepositorio.findAll();

  }
  
   public List<FichaMedica> listarFichasMedicasPorPaciente(Long idHistoriaClinica) {
    return fichaMedicaRepositorio.findHistoriaClinica(idHistoriaClinica);
  }
   
   public HistoriaClinica BuscarByPaciente(Long idPaciente) {
    return historiaClinicaRepositorio.buscarPorPaciente(idPaciente);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                   // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

}
