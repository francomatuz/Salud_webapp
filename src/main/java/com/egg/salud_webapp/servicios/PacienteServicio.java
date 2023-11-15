package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.HistoriaClinica;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.enumeraciones.Rol;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.PacienteRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    //Metodos Crud
    
    //Crear paciente
    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MiException {
        
        

        //Falta validador
        
        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);
        paciente.setEmail(email);

        paciente.setPassword(new BCryptPasswordEncoder().encode(password));

        paciente.setRol(Rol.USER);
        // Creacion de historia clinica
        HistoriaClinica historiaClinica = new HistoriaClinica();
        paciente.setHistoriaClinica(historiaClinica);

        pacienteRepositorio.save(paciente);
    }
    //Actualizar paciente
    @Transactional
    public void actualizar(String idPaciente, String nombre, String email, String password, String password2) throws MiException {

        //falta validador

        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);
        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();
            paciente.setNombre(nombre);
            paciente.setEmail(email);
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));

            paciente.setRol(Rol.USER);
            pacienteRepositorio.save(paciente);
        }
    }
    
    

}
