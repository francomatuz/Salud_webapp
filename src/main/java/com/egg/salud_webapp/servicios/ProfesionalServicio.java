
package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.entidades.ProfesionalPrestadores;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.ProfesionalPrestadoresRepositorio;
import com.egg.salud_webapp.repositorios.ProfesionalRepositorio;
import java.time.LocalDate;
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
    @Autowired
    ProfesionalPrestadoresRepositorio profesionalPrestadoresRepositorio;
    // Metodos Crud
    // Crear paciente

    @Transactional
    public void registrar(String matricula, Especialidades especialidad,
             Boolean atencionVirtual,
           String[] prestadores,  String nombre, String apellido, String dni,
            LocalDate fecha_nac,
            String email, String password, String password2, GeneroEnum genero) throws MiException {
        validarAtributos(nombre, apellido, email, dni, fecha_nac, password, password2, matricula);
                
        List<String> prestadoresList = convertirStringAListaDeObrasSociales(prestadores);
        Profesional profesional = new Profesional(matricula, especialidad,  atencionVirtual, 
                nombre, apellido, dni, fecha_nac, email, new BCryptPasswordEncoder().encode(password), genero,
                UsuarioEnum.USER);
        profesionalRepositorio.save(profesional);
        for (String prestador  : prestadoresList) {
            ProfesionalPrestadores profesionalPrestadores = new ProfesionalPrestadores(profesional,prestador);
            profesionalPrestadoresRepositorio.save(profesionalPrestadores);

            
        }
    }
     public List<String> convertirStringAListaDeObrasSociales(String[] prestadoresArray) {
        List<String> obrasSociales = new ArrayList<>();
        if (prestadoresArray != null) {
            for (String prestador : prestadoresArray) {
                obrasSociales.add(String.valueOf(prestador));
            }
        }
        return obrasSociales;
    }

    @Transactional
    public void actualizar(Long id, String nombre, String apellido, String dni, LocalDate fecha_nac, String email,
     List <ObraSocial> prestadores,GeneroEnum genero,
            String password, String password2
            )
            throws MiException {

        validarAtributos2(email, password, password2);
        Profesional profesionalAActualizar = getById(id);

        if (profesionalAActualizar != null) {

            profesionalAActualizar.setNombre(nombre != null ? nombre : profesionalAActualizar.getNombre());
            profesionalAActualizar.setApellido(apellido != null ? apellido : profesionalAActualizar.getApellido());
            profesionalAActualizar.setEmail(email != null ? email : profesionalAActualizar.getEmail());
            profesionalAActualizar.setDni(dni != null ? dni : profesionalAActualizar.getDni());
            profesionalAActualizar.setFecha_nac(fecha_nac != null ? fecha_nac : profesionalAActualizar.getFecha_nac());
            profesionalAActualizar.setPassword(password != null ? new BCryptPasswordEncoder().encode(password)
                    : profesionalAActualizar.getPassword());
            // profesionalAActualizar
            //         .setPrestadores(prestadores != null ? prestadores : profesionalAActualizar.getPrestadores());
            // profesionalAActualizar.setAtencionFisicaDireccion(
            //         direccion != null ? direccion : profesionalAActualizar.getAtencionFisicaDireccion());
            // profesionalAActualizar.setAtencionVirtual(
            //         atencionVirtual != null ? atencionVirtual : profesionalAActualizar.getAtencionVirtual());
            // profesionalAActualizar.setBio(bio != null ? bio : profesionalAActualizar.getBio());

          

            profesionalRepositorio.save(profesionalAActualizar);
            // el signo de pregunta y los dos puntos es como si fuera un IF
        }
    }

    // Eliminar un profesional
    @Transactional
    private void eliminar(Long id) throws MiException {
        profesionalRepositorio.delete(getById(id));
    }

    public boolean tieneBio(Long id) throws MiException {
        Profesional profesional = getById(id);

        if (profesional.getBio() == null || profesional.getBio() == "" || profesional.getBio().isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    // Listar profesionales
    public List<Profesional> listarProfesionales() {
        List<Profesional> profesionales = new ArrayList<>();
        profesionales = profesionalRepositorio.findAll();
        return profesionales;
    }

    // Buscar un profesional por id
    public Profesional getById(Long id) throws MiException {
        Profesional profesional = profesionalRepositorio.getById(id);
        if (profesional == null) {
            throw new MiException("No se encontró un profesional con los datos ingresados");
        } else {
            return profesional;
        }
    }

    // validar los atributos de creación
    private void validarAtributos(String nombre, String apellido, String email, String dni, LocalDate fecha_nac,
            String password, String password2, String matricula)
            throws MiException {

        Optional<Profesional> dniExistente = profesionalRepositorio.buscarPorDni(dni);
        Optional<Profesional> emailExistente = profesionalRepositorio.buscarPorEmail(email);
        // Optional<Profesional> matriculaExistente =
        // profesionalRepositorio.buscarPorMatricula(matricula);

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
        
        

    }

    // validar atributos de actualización
    private void validarAtributos2(String email, String password, String password2) throws MiException {

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
        // if (direccion.isEmpty() || direccion == null) {
        //     throw new MiException("El direccion no puede estar vacío o ser nulo");
        // }
        // if (bio.isEmpty() || bio == null) {
        //     throw new MiException("La bio no puede estar vacía o ser nula");
        // }

    }
}
