package com.egg.salud_webapp.repositorios;

import com.egg.salud_webapp.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface ImagenRepositorio extends JpaRepository<Imagen, Long> {
    @Query("SELECT i.data FROM Imagen i WHERE i.id = :id")
    byte[] obtenerDataPorId(@Param("id") Long id);
}


