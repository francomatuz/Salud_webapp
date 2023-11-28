package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.Profesional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, Long> {

    //Búsqueda del profesional por dni
    @Query("SELECT p FROM Profesional p WHERE p.dni = :dni")
    Optional<Profesional> buscarPorDni(@Param("dni") String dni);

    //Búsqueda del profesional por email
    @Query("SELECT p FROM Profesional p WHERE p.email = :email")
    public Profesional buscarPorEmail(@Param("email") String email);

    //Búsqueda del profesional por matrícula
    @Query("SELECT p FROM Profesional p WHERE p.matricula = :matricula")
    Optional<Profesional> buscarPorMatricula(@Param("matricula") String matricula);

    //Búsqueda de profesional por id
    @Query("SELECT p FROM Profesional p WHERE p.id = :id")
    Optional<Profesional> buscarPorId(@Param("id") Long id);

    //Búsqueda de profesionales por especialidad
    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    public List<Profesional> buscarPorEspecialidad(@Param("especialidad") String especialidad);

    // Consulta para buscar profesionales no aprobados
    @Query("SELECT p FROM Profesional p WHERE p.alta = 0")
    public List<Profesional> buscarProfesionalesNoAprobados();

    //Búsqueda por precio
    //Búsqueda de profesionales por obra social
    //Búsqueda de profesionales por dirección
    //Búsqueda de profesionales por atención virtual
}
