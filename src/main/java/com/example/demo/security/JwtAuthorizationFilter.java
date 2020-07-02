package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.example.demo.db.UserRepository;
import com.example.demo.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Classe qui hérite de basicAuthenticationFilter qui permet de gérer les autorisations de chaque requête avec Spring Security.
 */

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    /**
     * Permet d'essayer de récupérer l'utilisateur correspodant au token si il y en a un.
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Récupère la partie Authorization du header afin d'essayer de récuperer le token
        String header = request.getHeader(JwtProperties.HEADER);

        // Si il n'y a pas de token présent alors rien n'est fait et l'on sort de la methode
        if (header == null || !header.startsWith(JwtProperties.PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // Si un token est présent alors on récupère l'utilisateur correspondant à ce token
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }


    /**
     * Permet de tester que le token appartient bien a un utilisateur et de connaitre ses autorisations.
     *
     * @param request
     * @return Authentication
     */
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER)
                .replace(JwtProperties.PREFIX, "");

        if (token != null) {
            // récupère le username présent dans le token
            String userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            // On cherche dans la base de données si l'username est connu si c'est le cas on retourne ses droits
            if (userName != null) {
                User user = userRepository.findByUsername(userName);
                UserPrincipal principal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}
