package com.egg.salud_webapp.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date FechaUltimaModificacion;
    
    
    @OneToOne
    @JoinColumn(name = "paciente_id", unique = true)
    private Paciente paciente;

    public HistoriaClinica() {
    }
    public HistoriaClinica(Paciente paciente){
        this.paciente = paciente;
    }
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaUltimaModificacion() {
        return FechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date FechaUltimaModificacion) {
        this.FechaUltimaModificacion = FechaUltimaModificacion;
    }
    
    
    
    

}
