package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.enumeraciones.Estado;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.PacienteRepositorio;
import com.egg.salud_webapp.repositorios.TurnoRepositorio;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    @Transactional
    public void crearTurno(/*Profesional doctor,*/ String motivo, LocalDate fecha_turno, Date horario_turno, Boolean virtual_presencial, Estado estado) throws MiException {
        
        //validar(titulo, cuerpo);
        
        //Paciente paciente = pacienteRepositorio.findById(idPaciente).get();
        
        Turno turno = new Turno();
        
        //turno.setProfesional(doctor);
        
        turno.setMotivo(motivo);
        turno.setFecha_turno(fecha_turno);
        turno.setHorario_turno(horario_turno);
        turno.setVirtual_presencial(virtual_presencial);
        turno.setEstado(estado);
        
        turnoRepositorio.save(turno);
        
    }
    
    
    /*private void validar(String titulo, String cuerpo) throws MiException{
        
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo de la Noticia no puede ser nulo o estar vacio");
        }
        
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("El cuerpo de la Noticia no puede ser nulo o estar vacio");
        }
        
    }*/
    

}
