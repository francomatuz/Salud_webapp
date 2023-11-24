/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.FichaMedica;
import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.repositorios.FichaMedicaRepositorio;
import com.egg.salud_webapp.repositorios.HistoriaClinicaRepositorio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FichaMedicaServicios implements UserDetailsService {

  @Autowired
  private FichaMedicaRepositorio fichaMedicaRepositorio;
  
  @Autowired
  private HistoriaClinicaRepositorio historiaClinicaRepositorio;
  
    public void crear(LocalDate fecha, String diagnostico, String tratamiento, String notas, Long Id ){
        
        // validar atributos
        
        FichaMedica fichaMedica = new FichaMedica();
        
        fichaMedica.setFecha(fecha);
        fichaMedica.setDiagnostico(diagnostico);
        fichaMedica.setTratamiento(tratamiento);
        fichaMedica.setNotas(notas);
        
         HistoriaClinica historiaClinica = historiaClinicaRepositorio.buscarPorId(Id);
   
         if(historiaClinica != null){
             
             fichaMedica.setHistoriaClinica(historiaClinica);
             fichaMedicaRepositorio.save(fichaMedica);
             List<FichaMedica> fichasMedicas = historiaClinica.getFichasMedicas();
             fichasMedicas.add(fichaMedica);
             historiaClinica.setFichasMedicas(fichasMedicas);
             historiaClinicaRepositorio.save(historiaClinica);
         }else {
             throw new RuntimeException("No se encontró la historia clínica con ID: " + Id);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
         }
    
    
