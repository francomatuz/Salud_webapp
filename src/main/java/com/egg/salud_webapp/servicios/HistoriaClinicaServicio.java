
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.repositorios.HistoriaClinicaRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriaClinicaServicio {
    
    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;
    //Busquedas
    public List<HistoriaClinica> buscarPorNombrePaciente (String nombre){
        return historiaClinicaRepositorio.buscarPorNombrePaciente(nombre);
    }
    public List<HistoriaClinica> buscarPorIdPaciente (String pacienteId){
        return historiaClinicaRepositorio.buscarPorIdPaciente(pacienteId);
    }
    
    

    //Registro de eventos
    // hacer que unicamente el profesional pueda actualizar
   public void actualizarHistoriaClinica(HistoriaClinica historiaClinica) {
        // Lógica de actualización
        historiaClinica.setFechaUltimaModificacion(new Date());
        historiaClinicaRepositorio.save(historiaClinica);
    }
      
    }
    
    
    //hacer acceso controlado
    

