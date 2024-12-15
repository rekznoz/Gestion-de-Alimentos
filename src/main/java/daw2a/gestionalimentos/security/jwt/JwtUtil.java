package daw2a.gestionalimentos.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase para la generación, validación y extracción de tokens JWT
 */
@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Clave generada automáticamente
    private final long jwtExpiration = 1000 * 60 * 60 * 10L; // 10 horas

    /**
     * Extraer el nombre de usuario del token
     *
     * @param token Token JWT
     * @return Nombre de usuario
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extraer la fecha de expiración del token
     *
     * @param token Token JWT
     * @return Fecha de expiración
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extraer un claim específico del token
     *
     * @param token          Token JWT
     * @param claimsResolver Función para extraer el claim
     * @param <T>            Tipo de dato del claim
     * @return Claim extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extraer todos los claims del token
     *
     * @param token Token JWT
     * @return Claims extraídos
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // Configurar la clave de firma
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }
    }

    /**
     * Verificar si el token ha expirado
     *
     * @param token Token JWT
     * @return Verdadero si ha expirado, falso en caso contrario
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generar un token JWT para un usuario
     *
     * @param userDetails Detalles del usuario
     * @return Token JWT
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Crear un token JWT
     *
     * @param claims  Claims del token
     * @param subject Asunto del token
     * @return Token JWT
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256) // Especificar clave y algoritmo
                .compact();
    }

    /**
     * Validar un token JWT
     *
     * @param token       Token JWT
     * @param userDetails Detalles del usuario
     * @return Verdadero si es válido, falso en caso contrario
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}