package daw2a.gestionalimentos.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para redirigir las peticiones HTTP a HTTPS
 */
@Configuration
public class HttpToHttpsRedirectConfig {

    /**
     * Redirige las peticiones HTTP a HTTPS
     * @return Configuración del servidor
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> redirectHttpToHttps() {
        return factory -> factory.addAdditionalTomcatConnectors(createHttpConnector());
    }

    /**
     * Crea un conector HTTP para redirigir al HTTPS
     * @return Conector HTTP
     */
    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443); // Redirige al puerto HTTPS
        return connector;
    }
}