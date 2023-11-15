
package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.Rol;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Paciente extends Usuario{
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
  
   @Enumerated(EnumType.STRING)
    private Rol rol;

  @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
  private HistoriaClinica historiaClinica;
  
  
    public Paciente() {
        this.historiaClinica = new HistoriaClinica(this);
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getActive() {
        return activo;
    }

    public void setActive(Boolean active) {
        this.activo = activo;
    }
    
    
}
