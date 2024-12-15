package daw2a.gestionalimentos.security;

import daw2a.gestionalimentos.security.jwt.JwtRequestFilter;
import daw2a.gestionalimentos.security.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración de seguridad
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Constructor
     * @param jwtRequestFilter Filtro de peticiones JWT
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Codificador de contraseñas
     * @return Codificador de contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración de autenticación
     * @param authConfig Configuración de autenticación
     * @return Gestor de autenticación
     * @throws Exception Excepción de configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configuración de seguridad
     * @param http Objeto de configuración de seguridad
     * @return Filtro de seguridad
     * @throws Exception Excepción de configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/authenticate", "/register").permitAll()
                        .requestMatchers("/prestamos/**").hasAnyRole("USUARIO","BIBLIOTECARIO") // Usuarios pueden gestionar préstamos
                        .requestMatchers("/libros/**").hasAnyRole("BIBLIOTECARIO", "USUARIO") // General para ver libros
                        .requestMatchers(("/autores/**")).hasAnyRole("BIBLIOTECARIO","USUARIO") // General para ver autores
                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                )
                .httpBasic(Customizer.withDefaults()) // Habilitar Basic Authentication
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless para JWT
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT

        return http.build();
    }
}