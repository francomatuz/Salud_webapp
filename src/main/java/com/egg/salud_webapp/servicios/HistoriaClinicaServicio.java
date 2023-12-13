
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.repositorios.FichaMedicaRepositorio;
import com.egg.salud_webapp.repositorios.HistoriaClinicaRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriaClinicaServicio {

    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;
    @Autowired
    private FichaMedicaRepositorio fichaMedicaRepositorio;

    public List<FichaMedica> listarTodasLasFichasMedicas() {
        return fichaMedicaRepositorio.findAll();

    }

    public HistoriaClinica getById(Long id) {
        return historiaClinicaRepositorio.getById(id);
    }
}
