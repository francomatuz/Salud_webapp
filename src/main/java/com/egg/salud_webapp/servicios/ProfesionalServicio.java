
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
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service
public class ProfesionalServicio implements UserDetailsService {
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    ProfesionalPrestadoresRepositorio profesionalPrestadoresRepositorio;

    @Transactional
    public void registrar(String matricula, Especialidades especialidad,
            Boolean atencionVirtual,
            String[] prestadores, String nombre, String apellido, String dni,
            LocalDate fecha_nac,
            String email, String password, String password2, GeneroEnum genero) throws MiException {
        validarAtributos(prestadores, nombre, apellido, email, dni, fecha_nac, password, password2, matricula);

        List<String> prestadoresList = convertirStringAListaDeObrasSociales(prestadores);

        Profesional profesional = new Profesional(matricula, especialidad,
                atencionVirtual != null ? atencionVirtual : false,
                nombre, apellido, dni, fecha_nac, email, new BCryptPasswordEncoder().encode(password), genero,
                UsuarioEnum.USER);
        profesionalRepositorio.save(profesional);

        for (String prestador : prestadoresList) {
            ProfesionalPrestadores profesionalPrestadores = new ProfesionalPrestadores(profesional, prestador);
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

    public List<ObraSocial> obtenerObrasSocialesPorIdProfesional(Long idProfesional) {
        List<ProfesionalPrestadores> obrasSociales = profesionalPrestadoresRepositorio
                .findByProfesionalId(idProfesional);
        List<ObraSocial> obrasSocialesList = new ArrayList<>();

        for (ProfesionalPrestadores profesionalPrestadores : obrasSociales) {
            obrasSocialesList.add(ObraSocial.valueOf(profesionalPrestadores.getObraSocial()));
        }
        return obrasSocialesList;
    }

    @Transactional
    public void actualizar(Long id, String nombre, String apellido, String dni, LocalDate fecha_nac, String email,
            List<ObraSocial> prestadores, GeneroEnum genero,
            String password, String password2) throws MiException {

        validarAtributos2(id, email, password, password2);
        Profesional profesionalAActualizar = getById(id);

        if (profesionalAActualizar != null) {
            profesionalAActualizar.setNombre(nombre != null ? nombre : profesionalAActualizar.getNombre());
            profesionalAActualizar.setApellido(apellido != null ? apellido : profesionalAActualizar.getApellido());
            profesionalAActualizar.setEmail(email != null ? email : profesionalAActualizar.getEmail());
            profesionalAActualizar.setDni(dni != null ? dni : profesionalAActualizar.getDni());
            profesionalAActualizar.setFecha_nac(fecha_nac != null ? fecha_nac : profesionalAActualizar.getFecha_nac());
            profesionalAActualizar.setGenero(genero != null ? genero : profesionalAActualizar.getGenero());
            profesionalAActualizar.setPassword(password != null ? new BCryptPasswordEncoder().encode(password)
                    : profesionalAActualizar.getPassword());

            // profesionalAActualizar.setAtencionFisicaDireccion(
            // direccion != null ? direccion :
            // profesionalAActualizar.getAtencionFisicaDireccion());
            // profesionalAActualizar.setAtencionVirtual(
            // atencionVirtual != null ? atencionVirtual :
            // profesionalAActualizar.getAtencionVirtual());
            // profesionalAActualizar.setBio(bio != null ? bio :
            // profesionalAActualizar.getBio());
            if (prestadores == null)  throw new MiException("Se tiene que seleccionar al menos una opcion");
            
            List<String> obrasSocialesList = new ArrayList<>(); // lista con las obras sociales nuevas
            for (ObraSocial obraSocial : prestadores) {
                obrasSocialesList.add(obraSocial.toString());
            }
            profesionalPrestadoresRepositorio.deleteByProfesionalId(id); 

            for (String prestador : obrasSocialesList) { // creo una nueva lista con los prestadores nuevos
                ProfesionalPrestadores profesionalPrestadores = new ProfesionalPrestadores(profesionalAActualizar,
                        prestador);
                profesionalPrestadoresRepositorio.save(profesionalPrestadores);
            }
            Hibernate.initialize(profesionalAActualizar.getPrestadores());
            profesionalRepositorio.save(profesionalAActualizar);
        }
    }

    @Transactional
    public void eliminar(Long id) throws MiException {
        profesionalPrestadoresRepositorio.deleteById(id);
        profesionalRepositorio.delete(getById(id));

    }
    //Boton para cambiar el estado de baja
    public void darBaja(Long id) throws MiException{
       Profesional profesional = getById(id);
       if(profesional.getAlta()){
           profesional.setAlta(false);
           profesional.setRol(UsuarioEnum.BAJA);
       }
    }
    public boolean tieneBio(Long id) throws MiException {
        Profesional profesional = getById(id);
        return !(profesional.getBio() == null || profesional.getBio() == "" || profesional.getBio().isEmpty());     
    }

    // Listar profesionales
    public List<Profesional> listarProfesionales() {
        return profesionalRepositorio.findAll();
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

    @Transactional(readOnly = true)
    public List<ProfesionalPrestadores> obtenerObrasSocialesPorProfesional(Long idProfesional) {
        return profesionalPrestadoresRepositorio.findByProfesionalId(idProfesional);
    }

    // validar los atributos de creación
    private void validarAtributos(String[] prestadores, String nombre, String apellido, String email, String dni,
            LocalDate fecha_nac,
            String password, String password2, String matricula)
            throws MiException {

        Optional<Profesional> dniExistente = profesionalRepositorio.buscarPorDni(dni);
        Profesional emailExistente = profesionalRepositorio.buscarPorEmail(email);
        // Optional<Profesional> matriculaExistente =
        // profesionalRepositorio.buscarPorMatricula(matricula);
        if (prestadores == null) {
            throw new MiException("Se tiene que seleccionar al menos una opcion");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede estar vacío o ser nulo");
        }
        if (emailExistente != null && emailExistente.getEmail().equalsIgnoreCase(email)) {
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
    private void validarAtributos2(Long id, String email, String password, String password2) throws MiException {

        Profesional emailExistente = profesionalRepositorio.buscarPorEmail(email);

        if (emailExistente != null && !emailExistente.getId().equals(id)
                && emailExistente.getEmail().equalsIgnoreCase(email)) {
            throw new MiException("Ya hay un usuario existente con el Email ingresado");
        }

        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede estar vacío o ser nulo");
        }

        // if (password.isEmpty() || password == null || password.length() <= 5) {
        // throw new MiException("La contraseña no puede estar vacia y debe tener más de
        // 5 dígitos");
        // }
        // if (!password.equals(password2)) {
        // throw new MiException("La contraseñas ingresadas deben ser iguales");
        // }
        // if (direccion.isEmpty() || direccion == null) {
        // throw new MiException("El direccion no puede estar vacío o ser nulo");
        // }
        // if (bio.isEmpty() || bio == null) {
        // throw new MiException("La bio no puede estar vacía o ser nula");
        // }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Profesional profesional = profesionalRepositorio.buscarPorEmail(email);

        if (profesional != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + profesional.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", profesional);

            return new User(profesional.getEmail(), profesional.getPassword(), permisos);
        } else {
            return null;
        }

    }
}
