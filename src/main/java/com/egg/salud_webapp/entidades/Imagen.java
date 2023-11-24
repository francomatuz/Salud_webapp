package com.egg.salud_webapp.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import javax.persistence.*;

@Entity
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data; // Almacenar la imagen como un array de bytes

    // Relación OneToOne con Paciente
    @OneToOne(mappedBy = "imagen", cascade = CascadeType.ALL)
    private Paciente paciente;

    // Relación OneToOne con Profesional
    @OneToOne(mappedBy = "imagen", cascade = CascadeType.ALL)
    private Profesional profesional;

// Constructor sin argumentos requerido por JPA
    public Imagen() {

    }
    // Constructores, getters y setters
    
    // Constructor con parámetros
    public Imagen(byte[] data, Paciente paciente, Profesional profesional) {
        this.data = data;
        this.paciente = paciente;
        this.profesional = profesional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

}

