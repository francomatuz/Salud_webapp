package com.egg.salud_webapp.entidades;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class FichaMedica {

  // la ficha media debe crearse cuando se reserva un turno
  // y no se da de baja

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  private LocalDate fecha;
  private String diagnostico;
  private String tratamiento;
  private String notas; // Observaciones.

  @ManyToOne
  @JoinColumn(name = "historia_clinica_id")
  private HistoriaClinica historiaClinica;
  
   @OneToOne
   @JoinColumn(name = "profesional_id")
   private Profesional profesional;

  public FichaMedica() {
  }

  public FichaMedica(
      Long id,
      LocalDate fecha,
      String diagnostico,
      String tratamiento,
      String notas,
      HistoriaClinica historiaClinica,
      Profesional profesional) {
    this.id = id;
    this.fecha = fecha;
    this.diagnostico = diagnostico;
    this.tratamiento = tratamiento;
    this.notas = notas;
    this.historiaClinica = historiaClinica;
    this.profesional = profesional;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public String getDiagnostico() {
    return diagnostico;
  }

  public void setDiagnostico(String diagnostico) {
    this.diagnostico = diagnostico;
  }

  public String getTratamiento() {
    return tratamiento;
  }

  public void setTratamiento(String tratamiento) {
    this.tratamiento = tratamiento;
  }

  public String getNotas() {
    return notas;
  }

  public void setNotas(String notas) {
    this.notas = notas;
  }

  public HistoriaClinica getHistoriaClinica() {
    return historiaClinica;
  }

  public void setHistoriaClinica(HistoriaClinica historiaClinica) {
    this.historiaClinica = historiaClinica;
  }

  @Override
  public String toString() {
    return ("FichaMedica{" +
        "\nid=" +
        id +
        ", \nfecha=" +
        fecha +
        ", \ndiagnostico=" +
        diagnostico +
        ", \ntratamiento=" +
        tratamiento +
        ", \nnotas=" +
        notas +
        ", \nhistoriaClinica=" +
        historiaClinica +
        '}');
  }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }
  
}
