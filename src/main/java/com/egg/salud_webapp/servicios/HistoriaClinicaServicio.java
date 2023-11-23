
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.repositorios.HistoriaClinicaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoriaClinicaServicio {
    
    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;
    
    
    @Transactional
    public void agregarFichaMedica(Long id, FichaMedica fichaMedica){
        
        HistoriaClinica historiaClinica = historiaClinicaRepositorio.getById(id);
        
       fichaMedica.setHistoriaClinica(historiaClinica)
        
     historiaClinica.getFichasMedicas().add(fichaMedica);
     
     historiaClinicaRepositorio.save(historiaClinica);
        
    }
    
     public HistoriaClinica getById(Long id) {
        return historiaClinicaRepositorio.getById(id);
    }
}
