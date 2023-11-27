
package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Paciente extends Usuario{

    private Boolean activo;
 
   @Enumerated(EnumType.STRING)
   private ObraSocial obraSocial;
   
//  @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
//  private HistoriaClinica historiaClinica;
  
 /*   @OneToOne
    private Imagen imagen;
  */

    public Paciente() {
    }

    public Paciente(Boolean activo, ObraSocial obraSocial, String nombre, String apellido, String dni, LocalDate fecha_nac, String email, String password, GeneroEnum genero, UsuarioEnum rol) {
        super(nombre, apellido, dni, fecha_nac, email, password, genero, rol);
        this.activo = activo;
        this.obraSocial = obraSocial;
//        this.historiaClinica = historiaClinica;
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

//    public HistoriaClinica getHistoriaClinica() {
//        return historiaClinica;
//    }
//
//    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
//        this.historiaClinica = historiaClinica;
//    }
     
}
