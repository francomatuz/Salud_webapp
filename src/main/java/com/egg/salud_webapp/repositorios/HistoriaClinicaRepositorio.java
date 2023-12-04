/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaClinicaRepositorio  extends JpaRepository<HistoriaClinica, Long> {
   
        @Query ("SELECT hc FROM HistoriaClinica hc WHERE hc.id = :id")
          HistoriaClinica buscarPorId(@Param("id") Long id);   
    
    
}
