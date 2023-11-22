
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.ProfesionalRepositorio;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesionalServicio {
    
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;
    //Metodos Crud
    //Crear paciente
    
    @Transactional
    
    public void registrar(String matricula, Especialidades especialidad, LocalDateTime agendaTurnos, Integer duracionTurno, Double precio, Integer calificacion, String direccion, Boolean atencionVirtual, String bio, String[] prestadores, Long id, String nombre, String apellido, String dni, LocalDate fecha_nac, String email, String password,String password2, GeneroEnum genero, UsuarioEnum rol, Boolean alta) throws MiException {
        validarAtributos(nombre,apellido,email,dni,fecha_nac,password,password2,matricula,precio,direccion,bio);
        
        Profesional profesional= new Profesional();

        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setEmail(email);
        profesional.setDni(dni);
        profesional.setFecha_nac(fecha_nac);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));

        profesional.setRol(UsuarioEnum.USER);
        
        
        // Creacion datos del profesional
        profesional.setMatricula(matricula);
        profesional.setEspecialidad(especialidad);
        profesional.setPrestadores(prestadores);
        profesional.setPrecio(precio);
        profesional.setDireccion(direccion);
        profesional.setAtencionVirtual(atencionVirtual);
        profesional.setBio(bio);
//        profesional.setAlta(false);

        profesionalRepositorio.save(profesional);
    }
    
    @Transactional
    private void actualizar(Long id,String email, String password, String password2, String[]prestadores, Double precio, String direccion,Boolean atencionVirtual, String bio, LocalDateTime agendaTurno) throws MiException {
        
        validarAtributos2(email, password, password2, precio,direccion,bio, agendaTurno);
        Optional<Profesional>respuesta = profesionalRepositorio.buscarPorId(id);
        
        if(respuesta.isPresent()){
            
            Profesional profesional = respuesta.get();
            profesional.setEmail(email);
            profesional.setPassword(new BCryptPasswordEncoder().encode(password));
            profesional.setPrestadores(prestadores);
            profesional.setPrecio(precio);
            profesional.setDireccion(direccion);
            profesional.setAtencionVirtual(atencionVirtual);
            profesional.setBio(bio);
            profesional.setAgendaTurnos(agendaTurno);
        }
    }
    
    //Eliminar un profesional
    @Transactional
    private void eliminar(Long id) throws MiException{
        Optional <Profesional> profesionalExistente = profesionalRepositorio.buscarPorId(id);
        if(profesionalExistente.isPresent()){
            profesionalRepositorio.delete(profesionalExistente.get());
        }else{
            throw new MiException("No se encontró un profesional con los datos ingresados");
        }
    }
    
    //Listar profesionales
    public List<Profesional> listarProfesionales (){
        List<Profesional>profesionales = new ArrayList<>();
        profesionales = profesionalRepositorio.findAll();
        return profesionales;
    } 
    
    //Buscar un profesional por id
    public Profesional getById(Long id){
      return profesionalRepositorio.getById(id);
    }
    
    
    //validar los atributos de creación
    private void validarAtributos(String nombre, String apellido, String email, String dni, LocalDate fecha_nac, String password, String password2,String matricula, Double precio, String direccion,String bio ) throws MiException {

        Optional<Profesional> dniExistente = profesionalRepositorio.buscarPorDni(dni);
        Optional<Profesional> emailExistente = profesionalRepositorio.buscarPorEmail(email);
       // Optional<Profesional> matriculaExistente = profesionalRepositorio.buscarPorMatricula(matricula);
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede estar vacío o ser nulo");
        }
        if (emailExistente.isPresent()) {
            throw new MiException("Ya hay un usuario existente con el Email ingresado");
        }
        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede estar vacío o ser nulo");
        }
        if (dniExistente.isPresent()) {
            throw new MiException("Ya hay un usuario existente con el Dni ingresado");
        }
        if (dni.isEmpty() || dni == null) {
            throw new MiException("El dni no puede estar vacío o ser nulo");
        }
        if (fecha_nac == null) {
            throw new MiException("La fecha de nacimiento no puede estar vacía ");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacia y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("La contraseñas ingresadas deben ser iguales");
        }
        if (matricula.isEmpty() || matricula == null) {
            throw new MiException("La matrícula no puede estar vacía o ser nula");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("El direccion no puede estar vacío o ser nulo");
        }
        if (bio.isEmpty() || bio == null) {
            throw new MiException("La bio no puede estar vacía o ser nula");
        }
        if(precio>0){
            throw new MiException("El precio debe ser mayor a 0");
        }        
    }
    
    //validar atributos de actualización
      private void validarAtributos2(String email, String password, String password2, Double precio, String direccion,String bio, LocalDateTime agendaTurno) throws MiException {

        Optional<Profesional> emailExistente = profesionalRepositorio.buscarPorEmail(email);
        

        if (emailExistente.isPresent()) {
            throw new MiException("Ya hay un usuario existente con el Email ingresado");
        }
        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede estar vacío o ser nulo");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacia y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("La contraseñas ingresadas deben ser iguales");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("El direccion no puede estar vacío o ser nulo");
        }
        if (bio.isEmpty() || bio == null) {
            throw new MiException("La bio no puede estar vacía o ser nula");
        }
        if(precio>0){
            throw new MiException("El precio debe ser mayor a 0");
        }        
    }
    
}
