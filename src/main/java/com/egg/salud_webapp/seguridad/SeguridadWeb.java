package com.egg.salud_webapp.seguridad;

import com.egg.salud_webapp.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    public PacienteServicio pacienteServicio;
    // @Autowired
    // public ProfesionalServicio profesionalServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pacienteServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .antMatchers("/admin/*").hasRole("ADMIN")
                        .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                        .permitAll())
                .sessionManagement(management -> management
                        .maximumSessions(1)// Limita a un maximo de 1 sesion por usuario
                        .expiredUrl("/login?expired=true") // Pagina a la que se redirige si la sesion ha expirado
                        .maxSessionsPreventsLogin(true)// Evita que un usuario inice sesion en una nueva sesion si ya
                                                       // alcanzo el limite
                        .and())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll())
                .csrf(csrf -> csrf
                        .disable());

    }
}
