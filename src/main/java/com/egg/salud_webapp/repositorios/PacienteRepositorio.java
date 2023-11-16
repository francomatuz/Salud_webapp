
package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {
    
    @Query ("SELECT p FROM Paciente p WHERE p.email = :email")
    public Paciente buscarPorEmail(@Param("email") String email);
    
    @Query ("SELECT p FROM Paciente WHERE p.dni = :dni")
    public Paciente buscarPorDni(@Param("dni") String dni);
    
    @Query ("SELECT p FROM Paciente p WHERE p.apellido = :apellido")
    public Paciente buscarPorApellido(@Param("apellido") String apellido);
    
    
}