
package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.HistoriaClinica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {
    // busca por nombre del paciente
    @Query("SELECT hc FROM HistoriaClinica hc JOIN hc.paciente p WHERE p.nombre = :nombre")
    List<HistoriaClinica> buscarPorNombrePaciente(@Param("nombre") String nombre);
    // busca por id del paciente  jhay que hacerlo por dni
    @Query("SELECT hc FROM HistoriaClinica hc WHERE hc.paciente.id = :pacienteId")
    List<HistoriaClinica> buscarPorIdPaciente(@Param("pacienteId") String pacienteId);
    
    
   
}
