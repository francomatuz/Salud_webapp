package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.entidades.Profesional;
import com.egg.salud_webapp.enumeraciones.GeneroEnum;
import com.egg.salud_webapp.enumeraciones.ObraSocial;
import com.egg.salud_webapp.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.egg.salud_webapp.servicios.ProfesionalServicio;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/perfil2")
public class PerfilProfesionalControlador {

@Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/actualizar")
    public String mostrarFormulario(ModelMap modelo, HttpSession session) {
        
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (profesionalLogueado == null) {
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir al inicio de sesión
            return "redirect:/login";
        }
        List<String> obrasSocialesSelected = new ArrayList<>();
        List<String> obrasSocialesList = new ArrayList<>();
        for (ObraSocial obraSocial : profesionalServicio.obtenerObrasSocialesPorIdProfesional(profesionalLogueado.getId())) {
            obrasSocialesSelected.add(obraSocial.toString());
        }
        for (ObraSocial obraSocial : ObraSocial.values()){
            obrasSocialesList.add(obraSocial.toString());
        }

        modelo.put("profesional", profesionalLogueado);
        modelo.put("generos", GeneroEnum.values());
        modelo.put("obrasSociales", obrasSocialesList);
        modelo.put("atencionVirtual", profesionalLogueado.getAtencionVirtual());
        modelo.put("prestadores", obrasSocialesSelected);

        return "actualizarprofesional.html"; // Nombre del formulario de actualización de perfil
    }


    @PostMapping("/actualizar")
    public String actualizarPerfil(@RequestParam String nombre, @RequestParam String apellido,@RequestParam String dni,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_nac,
            @RequestParam String email, 
            
            @RequestParam(value = "prestadores", required = false) List <ObraSocial> prestadores, @RequestParam GeneroEnum genero, Double precio, ModelMap modelo, HttpSession session)
            throws MiException {

        // Obtener el usuario logueado desde la sesión
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");

        // Verificar si el usuario está logueado
        if (profesionalLogueado == null) {
            // Manejar el caso en el que el usuario no está logueado, por ejemplo, redirigir al inicio de sesión
            return "redirect:/login";
        }

        try {
            // Actualizar el perfil del paciente (sin cambiar la contraseña)
            profesionalServicio.actualizar(profesionalLogueado.getId(), nombre, apellido, dni, fecha_nac, email, prestadores,
                    genero, null , null, precio);

            modelo.put("Exito", "Perfil actualizado exitosamente");
//TO DO : REDIRECT!
            return "index.html"; // Página de perfil actualizado

        } catch (MiException ex) {
            // Manejar excepciones
            Logger.getLogger(PerfilProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("Error", ex.getMessage());
            modelo.put("profesional", profesionalLogueado);

            return "error.html"; // Página de error
        }
    }

    //DAR DE BAJA
    @GetMapping("/darBaja")
    public String darBaja(HttpSession session, ModelMap modelo) throws MiException {       
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        profesionalServicio.darBaja(profesionalLogueado.getId());
       //logica para logout
       return "dashboardprofesional.html";   
    }
    
   @PostMapping("/darBaja")
    public String darBajaPost(HttpSession session, ModelMap modelo, HttpServletRequest request, HttpServletResponse response) throws MiException {
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        profesionalServicio.darBaja(profesionalLogueado.getId());

        invalidateSession(request);

        return "redirect:/darBaja"; 
    }
        
    private void invalidateSession(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    
    
    @GetMapping ("/solicitarAlta")
    public String solicitarAlta(HttpSession session, ModelMap modelo) throws MiException {    
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
         profesionalServicio.darAlta(profesionalLogueado.getId());
         
         return "dashboardprofesional.html";
    }
    
    
    //ELIMINAR
    @GetMapping("/eliminar")
    public String eliminar(HttpSession session, ModelMap modelo) throws MiException {
        
        Profesional profesionalLogueado = (Profesional) session.getAttribute("usuariosession");
        

        try {
            
            profesionalServicio.eliminar(profesionalLogueado.getId());


            return "index.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:../dashboardprofesional.html";
        }

    }
    
    @GetMapping("/dashboard2")
    public String dashboard() {
        return "dashboardprofesional.html";
    }
    
}