package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.Tipo;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.PacienteRepositorio;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteServicio implements UserDetailsService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo,String nombre, String apellido, String email, String dni, LocalDate fecha_nac,
            ObraSocial obraSocial, GeneroEnum genero, String password, String password2) throws MiException {

        validarAtributos(nombre, apellido, email, dni, fecha_nac, password, password2);
        
        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setEmail(email);
        paciente.setDni(dni);
        paciente.setFecha_nac(fecha_nac);
        paciente.setObraSocial(obraSocial);
        paciente.setGenero(genero);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        paciente.setRol(UsuarioEnum.USER);
        Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);
        
        pacienteRepositorio.save(paciente);
    }

    // Actualizar paciente
    @Transactional
    public void actualizar(Long id, String nombre, String apellido, String email, String dni, LocalDate fecha_nac,
            ObraSocial obraSocial, GeneroEnum genero, String password, String password2) throws MiException {

        validarAtributosActualizar(id,nombre, apellido, email, dni, fecha_nac);

        Optional<Paciente> respuesta = pacienteRepositorio.buscarPorId(id);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();
             
        
    
            paciente.setNombre(nombre != null && !nombre.isEmpty() ? nombre : paciente.getNombre());
            paciente.setApellido(apellido != null && !apellido.isEmpty() ? apellido : paciente.getApellido());
            paciente.setEmail(email != null && !email.isEmpty() ? email : paciente.getEmail());
            paciente.setDni(dni != null && !dni.isEmpty() ? dni : paciente.getDni());
            paciente.setFecha_nac(fecha_nac != null ? fecha_nac : paciente.getFecha_nac());
            paciente.setObraSocial(obraSocial != null ? obraSocial : paciente.getObraSocial());
            paciente.setGenero(genero != null ? genero : paciente.getGenero());
            paciente.setPassword(password != null && !password.isEmpty() && password2 != null && !password2.isEmpty()? new BCryptPasswordEncoder().encode(password): paciente.getPassword());
    
            pacienteRepositorio.save(paciente);
        }
    }

    @Transactional
    public void eliminar(Long id) throws MiException {

        Optional<Paciente> pacienteExistente = pacienteRepositorio.buscarPorId(id);

        if (pacienteExistente.isPresent()) {
            pacienteRepositorio.delete(pacienteExistente.get());
        } else {
            throw new MiException("No se encontro un paciente con los datos ingresados");
        }
    }

    public Paciente getById(Long id) {
        return pacienteRepositorio.getById(id);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepositorio.findAll();
    }

    @Transactional
    public void cambiarRol(Long id) {
        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            if (paciente.getRol().equals(UsuarioEnum.USER)) {

                paciente.setRol(UsuarioEnum.ADMIN);

            } else if (paciente.getRol().equals(UsuarioEnum.ADMIN)) {
                paciente.setRol(UsuarioEnum.USER);
            }
        }
    }

    private void validarAtributos(String nombre, String apellido, String email, String dni, LocalDate fecha_nac,
            String password, String password2) throws MiException {

        Paciente dniExistente = pacienteRepositorio.buscarPorDni(dni);
        Paciente emailExistente = pacienteRepositorio.buscarPorEmail(email);

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
        if (dniExistente != null && dniExistente.getDni().equals(dni)) {
            throw new MiException("Ya hay un usuario existente con el dni ingresado");
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
    }


     private void validarAtributosActualizar(Long id,String nombre, String apellido, String email, String dni, LocalDate fecha_nac) throws MiException {

        Paciente dniExistente = pacienteRepositorio.buscarPorDni(dni);
        Paciente emailExistente = pacienteRepositorio.buscarPorEmail(email);

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede estar vacío o ser nulo");
        }
        if (emailExistente != null && !emailExistente.getId().equals(id) && emailExistente.getEmail().equalsIgnoreCase(email)) {
            throw new MiException("Ya hay un usuario existente con el Email ingresado");
        }
    
        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede estar vacío o ser nulo");
        }
        if (dniExistente != null && !dniExistente.getId().equals(id) && dniExistente.getDni().equals(dni)) {
            throw new MiException("Ya hay un usuario existente con el dni ingresado");
        }
    
        if (dni.isEmpty() || dni == null) {
            throw new MiException("El dni no puede estar vacío o ser nulo");
        }
        if (fecha_nac == null) {
            throw new MiException("La fecha de nacimiento no puede estar vacía ");
        }
        // if (password.isEmpty() || password == null || password.length() <= 5) {
        //     throw new MiException("La contraseña no puede estar vacia y debe tener más de 5 dígitos");
        // }
        // if (!password.equals(password2)) {
        //     throw new MiException("La contraseñas ingresadas deben ser iguales");
        // } // HAY QUE HACER LA VERIFICACION DE LA PASS CUANDO ACTUALIZA
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Paciente paciente = pacienteRepositorio.buscarPorEmail(email);

        if (paciente != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + paciente.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", paciente);

            return new User(paciente.getEmail(), paciente.getPassword(), permisos);
        } else {
            return null;
        }

    }
        public Paciente getOne(Long id){
        return pacienteRepositorio.getOne(id);
    }
    
}
