
package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.ProfesionalPrestadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalPrestadoresRepositorio extends JpaRepository<ProfesionalPrestadores, Long> {
    
 
}