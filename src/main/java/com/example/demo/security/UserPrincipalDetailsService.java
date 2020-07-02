package com.example.demo.security;

import com.example.demo.db.UserRepository;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/***
 * Classe qui hérite de la classe UserDetailsService qui correspond à la gestion des utilisateurs par Spring Security.
 */

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Récupère un object UserPrincipal suivant un username.
     *
     * @param username User.username
     * @return UserDetails
     * @throws UsernameNotFoundException exc
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
