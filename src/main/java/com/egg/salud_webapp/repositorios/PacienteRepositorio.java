
package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.Paciente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
    
    @Query ("SELECT p FROM Paciente p WHERE p.email = :email")
    public Paciente buscarPorEmail(@Param("email") String email);
    
   @Query ("SELECT p FROM Paciente p WHERE p.dni = :dni")
    Optional<Paciente> buscarPorDni(@Param("dni") String dni);    
   /* @Query ("SELECT p FROM Paciente p WHERE p.apellido = :apellido")
    public Optional<Paciente> buscarPorApellido(@Param("apellido") String apellido);*/   
    @Query ("SELECT p FROM Paciente p WHERE p.id = :id")
    // para que no tire error en pacienteservicio, tuve que agregar el "Optional" en la linea 26
     Optional<Paciente> buscarPorId(@Param("id") Long id);   
}