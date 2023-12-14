package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.Especialidades;
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
    public Profesional buscarPorDni(@Param("dni") String dni);

    //Búsqueda del profesional por email
    @Query("SELECT p FROM Profesional p WHERE p.email = :email")
    public Profesional buscarPorEmail(@Param("email") String email);

    //Búsqueda del profesional por matrícula
    @Query("SELECT p FROM Profesional p WHERE p.matricula = :matricula")
    public Profesional buscarPorMatricula(@Param("matricula") String matricula);

    //Búsqueda de profesional por id
    @Query("SELECT p FROM Profesional p WHERE p.id = :id")
    Optional<Profesional> buscarPorId(@Param("id") Long id);

    //Búsqueda de profesionales por especialidad
    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    public List<Profesional> buscarPorEspecialidad(@Param("especialidad") String especialidad);

    // Consulta para buscar profesionales no aprobados
    @Query("SELECT p FROM Profesional p WHERE p.alta = 2")
    public List<Profesional> buscarProfesionalesConSolicitud();

    @Query("SELECT p FROM Profesional p WHERE p.alta = 0 OR p.alta = 1 ORDER BY p.alta DESC")
    public List<Profesional> buscarProfesionalesSinSolicitud();

    //Busqueda profesional por precio "A verificar"
    @Query("SELECT p FROM Profesional p WHERE p.precio BETWEEN :minPrecio AND :maxPrecio")
    public List<Profesional> buscarProfesionalesPorRangoDePrecio(@Param("minPrecio") double minPrecio, @Param("maxPrecio") double maxPrecio);

    //Búsqueda de profesionales por obra social
    @Query("SELECT p FROM Profesional p WHERE p.id IN (SELECT pp.profesional FROM ProfesionalPrestadores pp WHERE pp.obraSocial = :obraSocial)")
    public List<Profesional> buscarProfesionalesPorObra(@Param("obraSocial") String obraSocial);

    //Busqueda por Apellido
    @Query("SELECT p FROM Profesional p WHERE p.apellido = :apellido")
    public List<Profesional> buscarPorApellido(@Param("apellido") String apellido);

    @Query("SELECT p FROM Profesional p WHERE FUNCTION('BIT', p.atencionVirtual) = 1")
    public List<Profesional> buscarProfesionalesConAtencionVirtual();

    //Busqueda por calificacion
    @Query("SELECT p FROM Profesional p ORDER BY p.calificacion DESC")
    public List<Profesional> buscarProfesionalesPorCalificacion();

    @Query("SELECT p.id FROM Profesional p WHERE p.especialidad = :especialidad")
    List<Long> findIdsByEspecialidad(@Param("especialidad") Especialidades especialidad);

    @Query("SELECT p FROM Profesional p WHERE p.alta = 2")
    public List<Profesional> findAllActivos();

    //Posibilidad de hacer filtros o busquedas combinadas
}