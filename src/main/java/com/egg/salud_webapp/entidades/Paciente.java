
package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Paciente extends Usuario{

    private Boolean activo;
 
   @Enumerated(EnumType.STRING)
   private ObraSocial obraSocial;
   
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
   @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;
   
//  @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
//  private HistoriaClinica historiaClinica;
  
 

    public Paciente() {
    }

    public Paciente(Imagen imagen,Boolean activo, ObraSocial obraSocial, Long id, String nombre, String apellido, String dni, LocalDate fecha_nac, String email, String password, GeneroEnum genero, UsuarioEnum rol) {
        super(id, nombre, apellido, dni, fecha_nac, email, password, genero, rol);
        this.activo = activo;
        this.obraSocial = obraSocial;
        this.imagen = imagen;
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }  
    
}

//    public HistoriaClinica getHistoriaClinica() {
//        return historiaClinica;
//    }
//
//    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
//        this.historiaClinica = historiaClinica;
//    }