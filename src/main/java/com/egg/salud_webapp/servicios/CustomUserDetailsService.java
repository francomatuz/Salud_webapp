package com.egg.salud_webapp.servicios;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PacienteServicio pacienteServicio;
    private final ProfesionalServicio profesionalServicio;

    public CustomUserDetailsService(PacienteServicio pacienteServicio, ProfesionalServicio profesionalServicio) {
        this.pacienteServicio = pacienteServicio;
        this.profesionalServicio = profesionalServicio;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = pacienteServicio.loadUserByUsername(email);

        if (userDetails == null) {
            userDetails = profesionalServicio.loadUserByUsername(email);
        }

        return userDetails;
    }
}
