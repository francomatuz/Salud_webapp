package com.egg.salud_webapp.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String datosGeneralesDelPaciente;

    @OneToOne
    @JoinColumn(name = "paciente_id") // Asegúrate de que el nombre de la columna sea el correcto
    private Paciente paciente;

//    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "historiaClinica")
    private List<FichaMedica> fichasMedicas;

    public HistoriaClinica() {
    }

    public HistoriaClinica(String datosGeneralesDelPaciente, List<FichaMedica> fichasMedicas, Paciente paciente) {
        this.datosGeneralesDelPaciente = datosGeneralesDelPaciente;
        this.fichasMedicas = fichasMedicas;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatosGeneralesDelPaciente() {
        return datosGeneralesDelPaciente;
    }

    public void setDatosGeneralesDelPaciente(String datosGeneralesDelPaciente) {
        this.datosGeneralesDelPaciente = datosGeneralesDelPaciente;
    }

    public List<FichaMedica> getFichasMedicas() {
        return fichasMedicas;
    }

    public void setFichasMedicas(List<FichaMedica> fichasMedicas) {
        this.fichasMedicas = fichasMedicas;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "HistoriaClinica{" + 
                "\nid=" + id + '}';
    }

    
    
}
