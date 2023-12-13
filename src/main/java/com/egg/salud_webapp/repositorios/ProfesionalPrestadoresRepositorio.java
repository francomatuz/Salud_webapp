package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.ProfesionalPrestadores;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalPrestadoresRepositorio extends JpaRepository<ProfesionalPrestadores, Long> {

  List<ProfesionalPrestadores> findByProfesionalId(Long idProfesional);

  // @Query("DELETE FROM ProfesionalPrestadores p WHERE p.profesional.id =
  // :profesionalId")
  // void deleteByProfesionalId(Long profesionalId);

  @Modifying
  @Query("DELETE FROM ProfesionalPrestadores p WHERE p.profesional.id = :profesionalId")
  void deleteByProfesionalId(@Param("profesionalId") Long profesionalId);

}