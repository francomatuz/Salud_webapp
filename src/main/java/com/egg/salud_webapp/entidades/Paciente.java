
package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Entity
public class Paciente extends Usuario{

    private Boolean activo;
 
  


   @Enumerated(EnumType.STRING)
   private ObraSocial obraSocial;
   
  @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
  private HistoriaClinica historiaClinica;
  
 /*   @OneToOne
    private Imagen imagen;
  */
  
    public Paciente() {
        this.historiaClinica = new HistoriaClinica(this);
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public UsuarioEnum getRol() {
        return rol;
    }

    public void setRol(UsuarioEnum rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

 
    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }
    
    
}
