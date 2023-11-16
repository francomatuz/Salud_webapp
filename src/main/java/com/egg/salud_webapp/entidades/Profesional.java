package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Profesional extends Usuario{
    
    private String matricula;
    
    @Enumerated(EnumType.STRING)
    private Especialidades especialidad;
    
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime agendaTurnos;
    private Integer duracionTurno;
    private Double precio;
    private Integer calificacion;
    private String direccion;
    private Boolean atencionVirtual;
    private String bio;
    private String[] obraSocial;


    public Profesional(String matricula, Especialidades especialidad, LocalDateTime agendaTurnos, Integer duracionTurno, Double precio, Integer calificacion, String direccion, Boolean atencionVirtual, String bio, String[] obraSocial, Long id, String nombre, String apellido, String dni, LocalDate fecha_nac, String email, String password, UsuarioEnum rol) {
        super(id, nombre, apellido, dni, fecha_nac, email, password, rol);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.agendaTurnos = agendaTurnos;
        this.duracionTurno = duracionTurno;
        this.precio = precio;
        this.calificacion = calificacion;
        this.direccion = direccion;
        this.atencionVirtual = atencionVirtual;
        this.bio = bio;
        this.obraSocial = obraSocial;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Especialidades getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidades especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDateTime getAgendaTurnos() {
        return agendaTurnos;
    }

    public void setAgendaTurnos(LocalDateTime agendaTurnos) {
        this.agendaTurnos = agendaTurnos;
    }

    public Integer getDuracionTurno() {
        return duracionTurno;
    }

    public void setDuracionTurno(Integer duracionTurno) {
        this.duracionTurno = duracionTurno;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getAtencionVirtual() {
        return atencionVirtual;
    }

    public void setAtencionVirtual(Boolean atencionVirtual) {
        this.atencionVirtual = atencionVirtual;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String[] getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String[] obraSocial) {
        this.obraSocial = obraSocial;
    }

}
