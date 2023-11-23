
package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.Estado;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Turno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_turno; //ver id_turno
    
    //Va a quedar el id del paciente relacion con turno
    @OneToOne
    private Paciente paciente;

    //@OneToOne
    //private Profesional doctor;
    
    private String motivo;
    
    private LocalDate fecha_turno;
    
    private Date horario_turno;
    
    private Boolean virtual_presencial;
      
    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    //private Boolean tomado; //no entendemos el boolean y enum
    
    //La obra social sale del paciente, y va a estar por defecto en la vista

    public Turno() {
    }
       

    public Long getId_turno() {
        return id_turno;
    }

    public void setId_turno(Long id_turno) {
        this.id_turno = id_turno;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha_turno() {
        return fecha_turno;
    }

    public void setFecha_turno(LocalDate fecha_turno) {
        this.fecha_turno = fecha_turno;
    }

    public Date getHorario_turno() {
        return horario_turno;
    }

    public void setHorario_turno(Date horario_turno) {
        this.horario_turno = horario_turno;
    }

    public Boolean getVirtual_presencial() {
        return virtual_presencial;
    }

    public void setVirtual_presencial(Boolean virtual_presencial) {
        this.virtual_presencial = virtual_presencial;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    
    
}
