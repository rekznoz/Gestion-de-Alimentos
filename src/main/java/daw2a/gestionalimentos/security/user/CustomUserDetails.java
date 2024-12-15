package daw2a.gestionalimentos.security.user;

import daw2a.gestionalimentos.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Clase que implementa la interfaz UserDetails de Spring Security
 */
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve los roles del usuario
     * @return Roles del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       /*return usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.name()))
                .collect(Collectors.toList());*/
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
    }

    /**
     * Devuelve la contraseña del usuario
     * @return Contraseña del usuario
     */
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    /**
     * Devuelve el nombre de usuario
     * @return Nombre de usuario
     */
    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    /**
     * Comprueba si la cuenta del usuario ha expirado
     * @return Si la cuenta ha expirado
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Comprueba si la cuenta del usuario está bloqueada
     * @return Si la cuenta está bloqueada
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Comprueba si las credenciales del usuario han expirado
     * @return Si las credenciales han expirado
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Comprueba si el usuario está habilitado
     * @return Si el usuario está habilitado
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}