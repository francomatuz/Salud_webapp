package com.egg.salud_webapp.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.enumeraciones.Especialidades;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, Long> {

    @Query("SELECT t FROM Turno t WHERE t.disponible = true")
    List<Turno> findByDisponibleTrue();

    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente")
    List<Turno> findByPaciente(Paciente paciente);

    @Query("SELECT t FROM Turno t WHERE t.profesional.id = :idProfesional AND t.disponible = true")
    List<Turno> findByProfesionalIdAndDisponibleTrue(@Param("idProfesional") Long idProfesional);

    

    @Query("SELECT t FROM Turno t WHERE t.disponible = true ORDER BY t.fechaHora")
    List<Turno> findAllDisponiblesOrderByFechaHora();

    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente AND t.isCancelado = false")
    List<Turno> findByPacienteAndIsCanceladoFalse(@Param("paciente") Paciente paciente);

    @Query("SELECT t FROM Turno t WHERE t.profesional.especialidad = :especialidad AND t.disponible = true")
    List<Turno> findByProfesionalEspecialidadAndDisponibleTrue(@Param("especialidad") Especialidades especialidad);

    @Query("SELECT t FROM Turno t WHERE t.profesional.id IN :idsProfesionales AND t.disponible = true")
    List<Turno> findByProfesionalIdInAndDisponibleTrue(@Param("idsProfesionales") List<Long> idsProfesionales);

    @Query("SELECT t FROM Turno t WHERE t.disponible = false")
    List<Turno> findByDisponiblefalse();
}