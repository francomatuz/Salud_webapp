package com.egg.salud_webapp.entidades;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = ISO.TIME)

    private LocalDateTime fechaHora;
    private boolean disponible;
    @ManyToOne
    private Profesional profesional;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    @Column(name = "duracion_minutos")
    private int duracionEnMinutos;
    private boolean isFinalizado;
    private boolean isCancelado;
    private boolean isCalificado;

    public Turno() {
    }

    public Turno(Profesional profesional, LocalDateTime fechaHora, int duracionEnMinutos) {
        this.profesional = profesional;
        this.fechaHora = fechaHora;
        this.duracionEnMinutos = duracionEnMinutos;
        this.disponible = true;
    }

    public Turno(Long id, LocalDateTime fechaHora, boolean disponible, Profesional profesional, Paciente paciente, int duracionEnMinutos, boolean finalizado, boolean isCalificado) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.disponible = true; // se inicialia en true ya que esta disponible para que un paciente lo tome
        this.profesional = profesional;
        this.paciente = paciente;
        this.duracionEnMinutos = duracionEnMinutos;
        this.isFinalizado = false;  // se inicializa en false ya que no esta finalizado
        this.isCancelado=false;//se incializa en falso porque el turno no ha sido cancelado aun
        this.isCalificado=false;
    }

    public Turno(Paciente paciente) {
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public boolean isIsFinalizado() {
        return isFinalizado;
    }

    public void setIsFinalizado(boolean isFinalizado) {
        this.isFinalizado = isFinalizado;
    }

    public boolean isIsCancelado() {
        return isCancelado;
    }

    public void setIsCancelado(boolean isCancelado) {
        this.isCancelado = isCancelado;
    }

    public boolean isIsCalificado() {
        return isCalificado;
    }

    public void setIsCalificado(boolean isCalificado) {
        this.isCalificado = isCalificado;
    }

}