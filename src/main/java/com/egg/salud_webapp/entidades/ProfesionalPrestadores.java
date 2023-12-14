package com.egg.salud_webapp.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProfesionalPrestadores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    protected Profesional profesional;
    protected String obraSocial;

    public ProfesionalPrestadores() {
    }

    public ProfesionalPrestadores(Profesional profesional, String obraSocial) {
        this.profesional = profesional;
        this.obraSocial = obraSocial;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }
    @Override
    public String toString() {
        return  obraSocial  ;
    }
}
