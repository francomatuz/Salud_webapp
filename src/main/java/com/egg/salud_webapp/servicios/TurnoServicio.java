package com.egg.salud_webapp.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.PacienteRepositorio;
import com.egg.salud_webapp.repositorios.ProfesionalRepositorio;
import com.egg.salud_webapp.repositorios.TurnoRepositorio;

@Service
public class TurnoServicio {
    @Autowired
    TurnoRepositorio turnoRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    public List<Turno> obtenerTurnosDisponibles() { // Aca tenemos un metodo que muestra una LISTA con los turnos QUE
                                                    // AUN ESTAN DISPONIBLES PARA TOMAR
        return turnoRepositorio.findByDisponibleTrue();
    }

    public List<Long> obtenerIdsProfesionalesPorEspecialidad(Especialidades especialidad) {
        return profesionalRepositorio.findIdsByEspecialidad(especialidad);
    }

    public List<Turno> obtenerTurnosDisponiblesPorEspecialidad(String especialidad) {
        // Convierte la cadena de especialidad a un valor de Especialidades
        Especialidades especialidadEnum = Especialidades.valueOf(especialidad.toUpperCase());
        // Lista de id de profesional : se busca con un select id from profesional where
        // especialidad = especialidad
        // Una vez que tengo la lista de id's buscar por cada id los turnos disponibles
        // del profesional y eso agregarlo todo a una lista que es la que retornamos.
        return turnoRepositorio.findByProfesionalEspecialidadAndDisponibleTrue(especialidadEnum);
    }

    public List<String> obtenerEspecialidadesDisponibles() {
        return Arrays.asList(Especialidades.values())
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
    @Transactional
    public void tomarTurno(Long idTurno, Long idPaciente) throws MiException { // Aca tenemos un metodo en el cual un
                                                                               // paciente "agenda" turnos y los guarda,
                                                                               // con su id.
        Turno turnoATomar = getById(idTurno);
        if (turnoATomar != null && turnoATomar.getPaciente() == null && turnoATomar.isDisponible()) {
            Paciente paciente = pacienteRepositorio.findById(idPaciente).orElse(null);
            if (paciente != null) {
                turnoATomar.setPaciente(paciente);
                turnoATomar.setDisponible(false);
                turnoATomar.setIsCancelado(false); //
                turnoRepositorio.save(turnoATomar);

            } else {
                throw new MiException("Paciente no encontrado.");
            }
        } else {
            throw new MiException("Turno no valido para ser tomado.");
        }
    }

    public Turno getById(Long id) throws MiException {
        return turnoRepositorio.findById(id)
                .orElseThrow(() -> new MiException("No se encontró un turno con el ID: " + id));
    }

    public List<Turno> obtenerTurnosTomadosPorPaciente(Long idPaciente) throws MiException {
        Paciente paciente = pacienteRepositorio.findById(idPaciente).orElse(null);

        if (paciente != null) {
            return turnoRepositorio.findByPacienteAndIsCanceladoFalse(paciente);
        } else {
            throw new MiException("Paciente no encontrado.");
        }
    }
    public List<Turno> obtenerTurnosDisponiblesYAtencionVirtual(String especialidad) {
        Especialidades especialidadEnum = Especialidades.valueOf(especialidad.toUpperCase());
        return turnoRepositorio.findByProfesionalEspecialidadAndDisponibleTrueAndProfesionalAtencionVirtualTrue(especialidadEnum);
    }

    public List<Turno> obtenerTurnosDisponiblesPorProfesional(Long idProfesional) {
        return turnoRepositorio.findByProfesionalIdAndDisponibleTrue(idProfesional);
    }

    public List<Turno> obtenerTurnosDisponiblesPorIdsProfesionales(List<Long> idsProfesionales) {
        return turnoRepositorio.findByProfesionalIdInAndDisponibleTrue(idsProfesionales);
    }
    
    public List<Turno> obtenerTurnosParaElProfesional(Long idProfesional) {
        return turnoRepositorio.findByProfesionalAndIsCanceladoFalseAndIsFinalizadoFalse(idProfesional);
    }

    @Transactional
    public void eliminarTurno(Long idTurno) throws MiException {
        Turno turnoAEliminar = getById(idTurno);
        if (turnoAEliminar != null && turnoAEliminar.getPaciente() == null) {
            turnoRepositorio.delete(turnoAEliminar);
        } else {
            throw new MiException("Turno no valido para ser eliminado.");
        }
    }

    @Transactional
    public void modificarTurno(Long idTurno, LocalDate nuevaFecha, LocalTime nuevoHorario) throws MiException {
        Turno turnoAModificar = getById(idTurno);

        if (turnoAModificar != null) {
            LocalDateTime nuevaFechaHora = null;

            // Modificar la fecha si se proporciona una nueva
            if (nuevaFecha != null) {
                // Mantener la misma hora y minutos, pero con la nueva fecha
                nuevaFechaHora = LocalDateTime.of(nuevaFecha, turnoAModificar.getFechaHora().toLocalTime());
            }

            // Modificar el horario si se proporciona uno nuevo
            if (nuevoHorario != null) {
                // Mantener la misma fecha, pero con la nueva hora y minutos
                nuevaFechaHora = LocalDateTime.of(turnoAModificar.getFechaHora().toLocalDate(), nuevoHorario);
            }

            // Actualizar la fecha y hora del turno si se proporciona alguna modificación
            if (nuevaFechaHora != null) {
                turnoAModificar.setFechaHora(nuevaFechaHora);
            }

            // Guardar la modificación en el repositorio
            turnoRepositorio.save(turnoAModificar);
        } else {
            throw new MiException("Turno no encontrado para ser modificado.");
        }
    }

    @Transactional
    public void marcarTurnoComoFinalizado(Long idTurno) throws MiException {
        // Obtener el turno por ID
        Turno turno = getById(idTurno);

        // Verificar si el turno existe y no está finalizado
        if (turno != null && !turno.isIsFinalizado()) {
            // Marcar el turno como finalizado
            turno.setIsFinalizado(true);

            // Guardar el turno actualizado en la base de datos
            turnoRepositorio.save(turno);
        } else {
            // El turno no existe o ya está finalizado
            throw new MiException("No se puede marcar el turno como finalizado.");
        }
    }

    @Transactional
    public void cancelarTurno(Long turnoId) throws MiException {
        Turno turno = getById(turnoId);

        if (turno != null && !turno.isIsCancelado()) {
            // Marcar el turno como cancelado
            turno.setIsCancelado(true);

            // Marcar el turno como disponible nuevamente
            turno.setDisponible(true);

            // BORRAR EL PACIENTE QUE SE HABIA ASIGNADO
            turno.setPaciente(null);

            // Guardar el turno actualizado en la base de datos
            turnoRepositorio.save(turno);
        } else {
            // El turno no existe o ya está cancelado
            throw new MiException("No se puede cancelar el turno.");
        }
    }

    public List<Turno> obtenerTurnosTomados(){
        return turnoRepositorio.findByDisponiblefalse();
   
       }

       @Transactional
       public void calificar(Long turnoId) throws MiException {
           Turno turno = getById(turnoId);
       
               if (turno != null && !turno.isIsCalificado()) {
                   // Marcar el turno como cancelado
                   turno.setIsCalificado(true);
       
                   // Guardar el turno actualizado en la base de datos
                   turnoRepositorio.save(turno);
               } else {
                   // El turno no existe o ya está cancelado
                   throw new MiException("No se puede calificar el turno.");
               }
           }

}