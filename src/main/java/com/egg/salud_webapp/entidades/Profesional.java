package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Profesional extends Usuario {

    private String matricula;

    @Enumerated(EnumType.STRING)
    private Especialidades especialidad;

    @Column(columnDefinition = "TIMESTAMP")
    // private LocalDateTime agendaTurnos; no se usa
    // private Integer duracionTurno Se hace en la entity turno, y no necesariamente
    // se instancia , haces fecha de inicio y fecha fin
    // private Double precio; tambien se hace en la entity turno.
    // private Integer calificacion; Seria un double. Pero, hay que crear una tabla
    // Calificaciones que tenga idProfesional y la calificacion que le da el
    // paciente al final del turno. Y aqui tiene que ser el promedio de las que
    // tengan el id del profesional que quiero ver.
    private String atencionFisicaDireccion; // cambio de nombre, seguir un patron en el nombramiento de variables.
    private Boolean atencionVirtual;
    private String bio;
    private ObraSocial[] prestadores; // esto es un array de obras sociales, por lo tanto se usa el enum para crear el
                                      // array
    private Boolean alta;

    public Profesional(String matricula, Especialidades especialidad, String atencionFisicaDireccion,
            Boolean atencionVirtual, String bio, ObraSocial[] prestadores, String nombre, String apellido, String dni,
            LocalDate fecha_nac, String email, String password, GeneroEnum genero, UsuarioEnum rol) {
        super(nombre, apellido, dni, fecha_nac, email, password, genero, rol);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.atencionFisicaDireccion = atencionFisicaDireccion;
        this.atencionVirtual = atencionVirtual;
        this.bio = bio;
        this.prestadores = prestadores;
        

    }

    public Profesional() {
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

    public ObraSocial[] getPrestadores() {
        return prestadores;
    }

    public void setPrestadores(ObraSocial[] prestadores) {
        this.prestadores = prestadores;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public String getAtencionFisicaDireccion() {
        return atencionFisicaDireccion;
    }

    public void setAtencionFisicaDireccion(String atencionFisicaDireccion) {
        this.atencionFisicaDireccion = atencionFisicaDireccion;
    }

}
