
package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.Estado;
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
    private Long id;
    private Date horario;
    
    // cuando se crea un turno tambien se crea una ficha medica?
    
    
    //@OneToOne
    //private Profesional doctor;
    
    @OneToOne
    private Paciente paciente;
    
    @Enumerated(EnumType.STRING)
    protected Estado estado;
    
    private Boolean virtual_presencial;
    private String motivo;
    private Boolean tomado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Boolean getVirtual_presencial() {
        return virtual_presencial;
    }

    public void setVirtual_presencial(Boolean virtual_presencial) {
        this.virtual_presencial = virtual_presencial;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Boolean getTomado() {
        return tomado;
    }

    public void setTomado(Boolean tomado) {
        this.tomado = tomado;
    }
    
    
    
}
