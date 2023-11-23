
package com.egg.salud_webapp.entidades;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class HistoriaClinica {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String datosGeneralesDelPaciente; 
     
     @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval=true)
     private List<FichaMedica> fichasMedicas;

    public HistoriaClinica() {
    }

    public HistoriaClinica(String datosGeneralesDelPaciente, List<FichaMedica> fichasMedicas) {
        this.datosGeneralesDelPaciente = datosGeneralesDelPaciente;
        this.fichasMedicas = fichasMedicas;
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
    
    
}
