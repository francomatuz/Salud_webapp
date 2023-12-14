package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, Long> {

  @Query("SELECT hc FROM HistoriaClinica hc WHERE hc.id = :id")
  HistoriaClinica buscarPorId(@Param("id") Long id);
  
  @Query("SELECT hc FROM HistoriaClinica hc WHERE hc.paciente.id = :idPaciente")
  HistoriaClinica buscarPorPaciente(@Param("idPaciente") Long idPaciente);
  

}
