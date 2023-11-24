package com.egg.salud_webapp.controladores;

import com.egg.salud_webapp.entidades.Paciente;
import com.egg.salud_webapp.servicios.PacienteServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

//    @Autowired
//    private PacienteServicio pacienteServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }


    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Paciente logueado = (Paciente) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/index";
        }

        return "index.html";
    }
    // @PostMapping("/logincheck")
    // public String loginCheck(@RequestParam String email, @RequestParam String
    // password, ModelMap modelo) {
    // // Aquí deberías realizar la lógica de autenticación, por ejemplo, usando
    // Spring Security.
    // // Puedes usar el servicio de Spring Security o tu propio servicio para
    // verificar las credenciales.
    //
    // // Ejemplo simple (debes adaptarlo a tus necesidades):
    // if (email.equals("usuario@example.com") && password.equals("contraseña")) {
    // // Autenticación exitosa, puedes redirigir a la página de inicio u otra
    // página deseada.
    // return "redirect:/inicio";
    // } else {
    // // Autenticación fallida, agrega un mensaje de error y redirige a la página
    // de login.
    // modelo.put("error", "Usuario o contraseña incorrectos");
    // return "login.html";
    // }
    // }

}
