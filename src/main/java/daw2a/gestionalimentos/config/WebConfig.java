package daw2a.gestionalimentos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración global de CORS (Cross-Origin Resource Sharing) para permitir que
 * el frontend (u otras aplicaciones) accedan a recursos de este backend.
 */
@Configuration
public class WebConfig {

    /**
     * Configura y registra un filtro CORS global para la aplicación.
     * Este filtro permite controlar qué dominios, headers y métodos pueden
     * interactuar con el backend.
     *
     * @return un bean de tipo CorsFilter configurado.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = getCorsConfiguration();
        config.setAllowCredentials(false); // Permite credenciales (cookies, etc.)
        config.setMaxAge(3600L); // Tiempo de vida de la configuración
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // Retornar el filtro CORS configurado
        return new CorsFilter(source);
    }

    /**
     * Configura y retorna una instancia de CorsConfiguration con la configuración
     * de CORS deseada.
     *
     * @return una instancia de CorsConfiguration configurada.
     */
    private static CorsConfiguration getCorsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        //config.addAllowedOrigin("http://localhost:3000"); // Dominio permitido (cambiar según el dominio de frontend)
        config.addAllowedOrigin("*"); // Dominio permitido (cambiar según el dominio de frontend)
        config.addAllowedHeader("*"); // Permite todos los headers
        //config.addAllowedMethod("*"); // Permite todos los métodos (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        return config;
    }
}