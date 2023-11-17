
package com.egg.salud_webapp.entidades;

import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;

@Entity
@Inheritance
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected LocalDate fecha_nac;
    protected String email;
    protected String password;
    
    
    //@OneToOne
    //private Imagen imagen;
    @Enumerated(EnumType.STRING)
    protected GeneroEnum genero;
    
    @Enumerated(EnumType.STRING)
    protected UsuarioEnum rol;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellido, String dni, LocalDate fecha_nac, String email, String password, GeneroEnum genero, UsuarioEnum rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha_nac = fecha_nac;
        this.email = email;
        this.password = password;
        this.genero = genero;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioEnum getRol() {
        return rol;
    }

    public void setRol(UsuarioEnum rol) {
        this.rol = rol;
    }

    public GeneroEnum getGenero() {
        return genero;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

      
}
