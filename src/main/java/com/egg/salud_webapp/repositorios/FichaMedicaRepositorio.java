
package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.FichaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaMedicaRepositorio extends JpaRepository<FichaMedica,Long>{
    
  //  @Query
    
    
    
    
}
