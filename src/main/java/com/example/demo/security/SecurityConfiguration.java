package com.example.demo.security;

import com.example.demo.db.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;

/**
 * Class qui hérite de la classe WebSecurityConfigurerAdapter qui permet la gestion des méthodes d'authentification,
 * des différentes permissions et de la gestion des utilisateurs utilisés par Spring Security.
 */

@Configuration
@EnableWebSecurity
@CrossOrigin
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UserRepository userRepository;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UserRepository userRepository) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.userRepository = userRepository;

    }

    /**
     * Applique le daoAuthenticationProvider comme AuthenticationManagerBuilder.
     *
     * @param authenticationManagerBuilder authMana
     */

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * Mise en place de toutes les contraintes et des méthodes de gestion de la security de l'application.
     *
     * @param http httpSecurity
     * @throws Exception exc
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // provisoir, problème au niveau du CrossOrigin
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                // les sessions ne sont pas mémoriser
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedResponse())
                .and()
                .logout()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                //utilisation de l'api uniquement aux personnes authentifiées
                .antMatchers("/api/**").fullyAuthenticated()
                .anyRequest().permitAll();
    }

    /**
     * Renvoi une erreur si l'authentification echoue.
     *
     * @return AuthenticationEntryPoint
     */

    private AuthenticationEntryPoint unauthorizedResponse() {
        return (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }


    /**
     * Permet d'obtenir l'objet qui verifiera la que l'utilisateur est bien enregistré et que le password scripté correspondant bien à celui present dans la base de données
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    /**
     * Permet d'obtenir l'objet qui script les mot de passes.
     *
     * @return PasswordEncoder
     */

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
