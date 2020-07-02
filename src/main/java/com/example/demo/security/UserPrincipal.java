package com.example.demo.security;

import com.example.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Classe qui hérite de la classe UserDetails qui correspond au détails d'un utilisateur utilisé par Spring Security.
 */

public class UserPrincipal implements UserDetails {
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * Récupère la liste des permissions et des rôles de l'utilisateur.
     *
     * @return Collection<? extends GrantedAuthority>
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // récupère la liste des permissions
        this.user.getPermissionList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });

        // récupère la liste des rôles
        this.user.getRoleList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
            authorities.add(authority);
        });

        return authorities;
    }

    /**
     * Récupère le password de l'utilisateur
     *
     * @return String
     */

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * Récupère le username de l'utilisateur
     *
     * @return String
     */

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * Non utilisé
     *
     * @return boolean
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Non utilisé
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Non utilisé
     *
     * @return boolean
     */

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Permet d'enregistrer le compte comme compte actif à chaque connexion.
     *
     * @return boolean
     */

    @Override
    public boolean isEnabled() {
        return this.user.getActive() == 1;
    }
}
