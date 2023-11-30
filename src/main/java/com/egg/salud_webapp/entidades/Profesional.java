package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.SolicitudEnum;
import com.egg.salud_webapp.enumeraciones.Tipo;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


@Entity
public class Profesional extends Usuario {

    private String matricula;

    @Enumerated(EnumType.STRING)
    private Especialidades especialidad;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    
    // private LocalDateTime agendaTurnos; no se usa
    // private Integer duracionTurno Se hace en la entity turno, y no necesariamente
    // se instancia , haces fecha de inicio y fecha fin
    // private Integer calificacion; Seria un double. Pero, hay que crear una tabla
    // Calificaciones que tenga idProfesional y la calificacion que le da el
    // paciente al final del turno. Y aqui tiene que ser el promedio de las que
    // tengan el id del profesional que quiero ver.
    
    private String atencionFisicaDireccion; // cambio de nombre, seguir un patron en el nombramiento de variables.
    private Boolean atencionVirtual;
    private String bio;
    //ENUM PARA DIFERENCIAR ALTA/BAJA/SOLICITUD
    private SolicitudEnum alta = SolicitudEnum.SOLICITUD;
    private Double precio = 3000d;
    private Double calificacion = null; 

    @OneToMany(mappedBy = "profesional", fetch = FetchType.EAGER)
    public List<ProfesionalPrestadores> prestadores;
    
    

    public Profesional(String matricula, Especialidades especialidad,
            Boolean atencionVirtual, Double precio, String nombre, String apellido, String dni,
            LocalDate fecha_nac, String email, String password, GeneroEnum genero, UsuarioEnum rol, Imagen imagen) {
        super(nombre, apellido, dni, fecha_nac, email, password, genero, rol,imagen);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.atencionVirtual = atencionVirtual;
        this.precio = precio;
        this.tipo =  Tipo.PROFESIONAL;
        
        //setear aqui el activo
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

    public SolicitudEnum getAlta() {
        return alta;
    }

    public void setAlta(SolicitudEnum alta) {
        this.alta = alta;
    }

    public String getAtencionFisicaDireccion() {
        return atencionFisicaDireccion;
    }

    public void setAtencionFisicaDireccion(String atencionFisicaDireccion) {
        this.atencionFisicaDireccion = atencionFisicaDireccion;
    }

    public List<ProfesionalPrestadores> getPrestadores() {
        return prestadores;
    }

    public void setPrestadores(List<ProfesionalPrestadores> prestadores) {
        this.prestadores = prestadores;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    
}
