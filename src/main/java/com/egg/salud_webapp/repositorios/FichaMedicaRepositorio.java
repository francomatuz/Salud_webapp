
package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaMedicaRepositorio extends JpaRepository<FichaMedica, Long> {

    @Query("SELECT fm FROM FichaMedica fm WHERE fm.id = :id")
    public FichaMedica buscarPorId(@Param("id") Long id);
    
    @Query("SELECT f FROM FichaMedica f WHERE f.historiaClinica.id = :idHistoriaClinica")
    List<FichaMedica> findHistoriaClinica(@Param("idHistoriaClinica") Long idHistoriaClinica);

}
