package com.egg.salud_webapp.servicios;

import com.egg.salud_webapp.entidades.Imagen;
import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.entidades.ProfesionalPrestadores;
import com.egg.salud_webapp.entidades.Turno;
import com.egg.salud_webapp.enumeraciones.Especialidades;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.enumeraciones.SolicitudEnum;
import com.egg.salud_webapp.enumeraciones.Tipo;
import com.egg.salud_webapp.enumeraciones.UsuarioEnum;
import com.egg.salud_webapp.excepciones.MiException;
import com.egg.salud_webapp.repositorios.ProfesionalPrestadoresRepositorio;
import com.egg.salud_webapp.repositorios.ProfesionalRepositorio;
import com.egg.salud_webapp.repositorios.TurnoRepositorio;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
public class ProfesionalServicio implements UserDetailsService {

    @Autowired
    ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    ProfesionalPrestadoresRepositorio profesionalPrestadoresRepositorio;
    @Autowired
    ImagenServicio imagenServicio;
    @Autowired
    TurnoRepositorio turnoRepositorio;

    @Transactional
    public void registrar(MultipartFile archivo, String matricula, Especialidades especialidad,
            Boolean atencionVirtual, Double precio,
            String[] prestadores, String nombre, String apellido, String dni,
            LocalDate fecha_nac,
            String email, String password, String password2, GeneroEnum genero) throws MiException {
        validarAtributos(archivo, prestadores, nombre, apellido, email, dni, fecha_nac, password, password2,
                matricula /* precio */);

        List<String> prestadoresList = convertirStringAListaDeObrasSociales(prestadores);

        Imagen imagen = null;
        Tipo tipoUsuario = Tipo.PROFESIONAL;

        imagen = imagenServicio.guardar(archivo, tipoUsuario);

        Profesional profesional = new Profesional(matricula, especialidad,
                atencionVirtual != null ? atencionVirtual : false, precio,
                nombre, apellido, dni, fecha_nac, email, new BCryptPasswordEncoder().encode(password), genero,
                UsuarioEnum.USER, imagen);

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
    public void actualizar(Profesional profesional, MultipartFile archivo, String nombre, String apellido, String dni,
            String matricula, LocalDate fecha_nac, String email,
            List<ObraSocial> prestadores, GeneroEnum genero,
            String password, String password2, Double precio) throws MiException {

        validarAtributosActualizar(profesional, archivo, nombre, apellido, dni, matricula, fecha_nac, email,
                prestadores, precio);
        Profesional profesionalAActualizar = getById(profesional.getId());

        if (profesionalAActualizar != null) {
            profesionalAActualizar.setNombre(nombre != null ? nombre : profesionalAActualizar.getNombre());
            profesionalAActualizar.setApellido(apellido != null ? apellido : profesionalAActualizar.getApellido());
            profesionalAActualizar.setEmail(email != null ? email : profesionalAActualizar.getEmail());
            profesionalAActualizar.setDni(dni != null ? dni : profesionalAActualizar.getDni());
            profesionalAActualizar.setFecha_nac(fecha_nac != null ? fecha_nac : profesionalAActualizar.getFecha_nac());
            profesionalAActualizar.setGenero(genero != null ? genero : profesionalAActualizar.getGenero());
            profesionalAActualizar.setPassword(password != null ? new BCryptPasswordEncoder().encode(password)
                    : profesionalAActualizar.getPassword());
            profesionalAActualizar.setPrecio(precio != null ? precio : profesionalAActualizar.getPrecio());

            List<String> obrasSocialesList = new ArrayList<>(); // lista con las obras sociales nuevas
            for (ObraSocial obraSocial : prestadores) {
                obrasSocialesList.add(obraSocial.toString());
            }
            profesionalPrestadoresRepositorio.deleteByProfesionalId(profesional.getId());

            if (archivo != null && !archivo.isEmpty()) {
                String idImagen = null;
                if (profesionalAActualizar.getImagen() != null) {
                    idImagen = profesionalAActualizar.getImagen().getId();
                }
                Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
                profesionalAActualizar.setImagen(imagen);
            }

            for (String prestador : obrasSocialesList) { // creo una nueva lista con los prestadores nuevos
                ProfesionalPrestadores profesionalPrestadores = new ProfesionalPrestadores(profesionalAActualizar,
                        prestador);
                profesionalPrestadoresRepositorio.save(profesionalPrestadores);
            }
        }
    }

    @Transactional
    public void eliminar(Long id) throws MiException {
        profesionalPrestadoresRepositorio.deleteByProfesionalId(id);

        // Eliminar el registro en la tabla principal (profesional)
        profesionalRepositorio.deleteById(id);

    }

    // Boton para cambiar el estado de baja
    public void darBaja(Long id) throws MiException {
        Profesional profesional = getById(id);
        if (profesional.getAlta() == SolicitudEnum.ACTIVO) {
            profesional.setAlta(SolicitudEnum.INACTIVO);
        }
        profesionalRepositorio.save(profesional);
    }

    public void darAlta(Long id) throws MiException {
        Profesional profesional = getById(id);
        profesional.setAlta(SolicitudEnum.ACTIVO);
        profesionalRepositorio.save(profesional);
    }

    @Transactional
    public void cambiarRol(Long id) {
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();

            if (profesional.getRol().equals(UsuarioEnum.USER)) {

                profesional.setRol(UsuarioEnum.MOD);

            } else if (profesional.getRol().equals(UsuarioEnum.MOD)) {
                profesional.setRol(UsuarioEnum.USER);
            }
        }
    }

    @Transactional
    public void cambiarEstado(Long id) {
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();

            if (profesional.getAlta().equals(SolicitudEnum.ACTIVO)) {

                profesional.setAlta(SolicitudEnum.INACTIVO);

            } else if (profesional.getAlta().equals(SolicitudEnum.INACTIVO)) {
                profesional.setAlta(SolicitudEnum.ACTIVO);
            }
        }
    }

    public List<Profesional> listarProfesionalesSolicitud() {
        return profesionalRepositorio.buscarProfesionalesConSolicitud();
    }

    public List<Profesional> listarProfesionalesSinSolicitud() {
        return profesionalRepositorio.buscarProfesionalesSinSolicitud();
    }

    public boolean tieneBio(Long id) throws MiException {
        Profesional profesional = getById(id);
        return !(profesional.getBio() == null || profesional.getBio() == "" || profesional.getBio().isEmpty());
    }

    // Listar profesionales
    public List<Profesional> listarProfesionales() {
        return profesionalRepositorio.findAll();
    }

    // Listar profesionales por precio
    public List<Profesional> listarProfesionalesPorPrecio(int num) {

        switch (num) {
            case 1:
                return profesionalRepositorio.buscarProfesionalesPorRangoDePrecio(0d, 3000d);

            case 2:
                return profesionalRepositorio.buscarProfesionalesPorRangoDePrecio(3001d, 5000d);

            case 3:
                return profesionalRepositorio.buscarProfesionalesPorRangoDePrecio(5001d, Double.MAX_VALUE);

        }
        return null;

    }

    // Listar profesionales por especialidad
    public List<Profesional> listarProfesionalesEspecialidad(String especialidad) {
        return profesionalRepositorio.buscarPorEspecialidad(especialidad);
    }

    // Listar profesionales por atecion virtual
    public List<Profesional> listarProfesionalesAtencionVirtual() {
        return profesionalRepositorio.buscarProfesionalesConAtencionVirtual();
    }

    // Listar profesionales por apellido
    public List<Profesional> listarProfesionalesPorApellido(String apellido) {
        return profesionalRepositorio.buscarPorApellido(apellido);
    }

    // Listar profesionales por obra social
    public List<Profesional> listarProfesionalesPorObraSocial(String obraSocial) {
        return profesionalRepositorio.buscarProfesionalesPorObra(obraSocial);
    }

    // Buscar un profesional por id
    public Profesional getById(Long id) {
        return profesionalRepositorio.getById(id);
    }

    @Transactional(readOnly = true)
    public List<ProfesionalPrestadores> obtenerObrasSocialesPorProfesional(Long idProfesional) {
        return profesionalPrestadoresRepositorio.findByProfesionalId(idProfesional);
    }

    // validar los atributos de creación
    private void validarAtributos(MultipartFile archivo, String[] prestadores, String nombre, String apellido,
            String email, String dni,
            LocalDate fecha_nac,
            String password, String password2, String matricula /* Double precio */)
            throws MiException {

        LocalDate fechaActual = LocalDate.now();

        Profesional dniExistente = profesionalRepositorio.buscarPorDni(dni);
        Profesional emailExistente = profesionalRepositorio.buscarPorEmail(email);
        // Optional<Profesional> matriculaExistente =
        // profesionalRepositorio.buscarPorMatricula(matricula);

        if (archivo.isEmpty() || archivo == null) {

        } else if (archivo.getSize() > 5 * 1024 * 1024 || !archivo.getContentType().startsWith("image")) {
            throw new MiException("El archivo debe ser una imagen y no debe superar los 5MB");
        }

        Profesional matriculaExistente = profesionalRepositorio.buscarPorMatricula(matricula);

        if (prestadores == null) {
            throw new MiException("Se tiene que seleccionar al menos una opcion");
        }
        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        if (apellido.isEmpty()) {
            throw new MiException("El apellido no puede estar vacío o ser nulo");
        }
        if (emailExistente != null && emailExistente.getEmail().equalsIgnoreCase(email)) {
            throw new MiException("Ya hay un usuario existente con el Email ingresado");
        }
        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede estar vacío o ser nulo");
        }
        if (dniExistente != null && dniExistente.getDni().equals(dni)) {
            throw new MiException("Ya hay un usuario existente con el Dni ingresado");
        }
        if (dni.isEmpty() || dni.length() < 7 || dni.length() > 8) {
            throw new MiException("El dni no puede estar vacío, ser nulo o debe tener 7 u 8 dígitos");
        }
        if (fecha_nac == null || fecha_nac.isAfter(fechaActual)) {
            throw new MiException("La fecha de nacimiento no puede estar vacía o ser posterior a la actual");
        }
        if (password.isEmpty() || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacia y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("La contraseñas ingresadas deben ser iguales");
        }
        if (matriculaExistente != null && matriculaExistente.getMatricula().equals(matricula)) {
            throw new MiException("Ya hay un usuario existente con la matricula ingresada");
        }

        if (matricula.isEmpty()) {
            throw new MiException("La matricula no puede estar vacía");
        }

    }

    // validar atributos de actualización
    private void validarAtributosActualizar(Profesional profesional, MultipartFile archivo, String nombre,
            String apellido, String dni, String matricula, LocalDate fecha_nac, String email,
            List<ObraSocial> prestadores,
            Double precio) throws MiException {

        LocalDate fechaActual = LocalDate.now();
        Profesional emailExistente = profesionalRepositorio.buscarPorEmail(email);
        Profesional dniExistente = profesionalRepositorio.buscarPorDni(dni);
        Profesional matriculaExistente = profesionalRepositorio.buscarPorMatricula(matricula);

        if (archivo.isEmpty() || archivo == null) {

        } else if (archivo.getSize() > 5 * 1024 * 1024 || !archivo.getContentType().startsWith("image")) {
            throw new MiException("El archivo debe ser una imagen y no debe superar los 5MB");
        }

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        if (apellido.isEmpty()) {
            throw new MiException("El apellido no puede estar vacío o ser nulo");
        }

        if (profesional.getDni().equals(dni)) {

        } else {
            if (dniExistente != null && dniExistente.getDni().equals(dni)) {
                throw new MiException("Ya hay un usuario existente con el dni ingresado");
            }

            if (dni.isEmpty() || dni.length() < 7 || dni.length() > 8) {
                throw new MiException("El dni no puede estar vacío, ser nulo o debe tener 7 u 8 dígitos");
            }
        }

        if (profesional.getMatricula().equals(matricula)) {

        } else {
            if (matriculaExistente != null && matriculaExistente.getMatricula().equals(matricula)) {
                throw new MiException("Ya hay un usuario existente con la matricula ingresada");
            }

            if (matricula.isEmpty()) {
                throw new MiException("La matricula no puede estar vacía");
            }
        }

        if (fecha_nac == null || fecha_nac.isAfter(fechaActual)) {
            throw new MiException("La fecha de nacimiento no puede estar vacía o ser posterior a la actual");
        }

        if (profesional.getEmail().equals(email)) {

        } else {
            if (emailExistente != null && emailExistente.getEmail().equalsIgnoreCase(email)) {
                throw new MiException("Ya hay un usuario existente con el Email ingresado");
            }

            if (email == null || email.isEmpty() || !email.contains("@")) {
                throw new MiException("El email no puede estar vacío, ser nulo y debe contener '@'");
            }
        }

        if (prestadores == null) {
            throw new MiException("Se tiene que seleccionar al menos una opcion");
        }

        // if (precio == null || precio == 0) {
        // throw new MiException("El precio no puede estar vacío o ser igual a cero");
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

    // Logica de los turnos
    @Transactional
    public List<Turno> generarTurnosDisponibles(Long id, LocalDate fechaInicio, LocalDate fechaFin,
            LocalTime horarioInicio, LocalTime horarioFin, int duracionTurnoEnMinutos) throws MiException {
        Profesional profesional = getById(id);
        List<Turno> turnosDisponibles = new ArrayList<>();

        DayOfWeek[] diasLaborables = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY};

        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            for (DayOfWeek diaLaborable : diasLaborables) {
                if (fecha.getDayOfWeek() == diaLaborable) {
                    LocalDateTime fechaHoraInicio = LocalDateTime.of(fecha, horarioInicio);
                    LocalDateTime fechaHoraFin = fecha.atTime(horarioFin);

                    while (fechaHoraInicio.isBefore(fechaHoraFin)) {
                        turnosDisponibles.add(new Turno(profesional, fechaHoraInicio, duracionTurnoEnMinutos));
                        fechaHoraInicio = fechaHoraInicio.plusMinutes(duracionTurnoEnMinutos);
                    }
                }
            }
        }

        // Guardar los turnos en la base de datos
        turnosDisponibles.forEach(turnoRepositorio::save);

        return turnosDisponibles;
    }

    public void calificacionProfesional(Long idProfesional, Integer calif) throws MiException {
        validarCalificacion(calif);

        Profesional profesional = profesionalRepositorio.getById(idProfesional);

        profesional.setCantCalificaciones(profesional.getCantCalificaciones() + 1);

        profesional.setSumaCalificaciones(profesional.getSumaCalificaciones() + calif);

        Integer calificacionTotal = (profesional.getSumaCalificaciones() / profesional.getCantCalificaciones());

        profesional.setCalificacion(calificacionTotal.doubleValue());

        profesionalRepositorio.save(profesional);
    }

    public void validarCalificacion(Integer calif) throws MiException {
        if (calif > 5) {
            throw new MiException("La calificacion no puede exceder al valor numero 5");
        } else if (calif < 0) {
            throw new MiException("La calificacion no puede ser menor a 0");
        }
    }

    @Transactional
    public void settearPrecioConsulta(Double precio, Long id) throws MiException {
        Profesional profesional = getById(id);
        profesional.setPrecio(precio);
        profesionalRepositorio.save(profesional);
    }

    public List<Profesional> listarProfesionalesActivos() {
        return profesionalRepositorio.findAllActivos();
    }

    public void guardarBio(Long idProfesional, String biografia) {
        Profesional profesional = profesionalRepositorio.getById(idProfesional);
        profesional.setBio(biografia);
        profesionalRepositorio.save(profesional);
    }
}
