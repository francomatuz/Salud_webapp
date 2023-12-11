package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.Tipo;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Paciente extends Usuario {

    private Boolean activo;

    @Enumerated(EnumType.STRING)
    private ObraSocial obraSocial;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistoriaClinica historiaClinica;
    @OneToMany(mappedBy = "paciente")
    private List<Turno> turnos;

    public Paciente() {
    }

    public Paciente(Boolean activo, ObraSocial obraSocial, String nombre, String apellido, String dni,
            LocalDate fecha_nac,
            String email, String password, GeneroEnum genero, UsuarioEnum rol, Tipo tipo, Imagen imagen) {
        super(nombre, apellido, dni, fecha_nac, email, password, genero, rol, imagen);
        this.activo = activo;
        this.obraSocial = obraSocial;
        // this.historiaClinica = historiaClinica;
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

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                super.toString() +
                ", activo=" + activo +
                ", obraSocial=" + obraSocial +
                ", tipo=" + tipo +
                ", historiaClinica=" + historiaClinica +
                '}';
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

}
