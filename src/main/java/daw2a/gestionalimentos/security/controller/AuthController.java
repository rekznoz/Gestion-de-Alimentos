package daw2a.gestionalimentos.security.controller;

import daw2a.gestionalimentos.dto.usuario.UsuarioCreateDTO;
import daw2a.gestionalimentos.entities.Usuario;
import daw2a.gestionalimentos.security.dto.AuthResponse;
import daw2a.gestionalimentos.security.dto.LoginUsuarioDTO;
import daw2a.gestionalimentos.security.jwt.JwtUtil;
import daw2a.gestionalimentos.security.user.CustomUserDetails;
import daw2a.gestionalimentos.services.TokenBlacklistService;
import daw2a.gestionalimentos.services.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para autenticación y registro de usuarios
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final TokenBlacklistService tokenBlacklistService;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService, TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    /**
     * Autenticar un usuario y devolver un token JWT
     * @param request Datos de autenticación del usuario
     * @param response Objeto de respuesta HTTP
     * @return Token JWT
     */
    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody LoginUsuarioDTO request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

        //Crear la cookie
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true); // Solo accesible desde el servidor
        jwtCookie.setSecure(true); // Solo en conexiones HTTPS
        jwtCookie.setPath("/"); // Disponible para toda la aplicación
        jwtCookie.setMaxAge(60 * 60 * 10); // Validez de 10 horas

        // Agregar la cookie a la respuesta
        response.addCookie(jwtCookie);
        return new AuthResponse(token);
    }

    /**
     * Cerrar sesión de un usuario
     * @param request Objeto de solicitud HTTP
     * @param response Objeto de respuesta HTTP
     * @return Mensaje de confirmación
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Manejar el token desde el encabezado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }

        // Manejar el token desde la cookie
        if (jwt == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                }
            }
        }

        // Si se encuentra el token, añadirlo a la blacklist
        if (jwt != null) {
            tokenBlacklistService.addTokenToBlacklist(jwt);

            // Eliminar la cookie
            Cookie jwtCookie = new Cookie("jwt", null);
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(0); // Caducar inmediatamente
            response.addCookie(jwtCookie);

            return ResponseEntity.ok("Logout exitoso. Token añadido a la blacklist y cookie eliminada.");
        }

        return ResponseEntity.badRequest().body("No se encontró un token válido para cerrar sesión.");
    }

    /**
     * Registrar un nuevo usuario y devolver un token JWT
     * @param crearUsuarioDTO Datos del nuevo usuario
     * @return Token JWT
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid UsuarioCreateDTO crearUsuarioDTO) {
        // Registrar usuario usando el servicio
        Usuario nuevoUsuario = usuarioService.registrarUsuario(crearUsuarioDTO);

        // Generar token JWT para el nuevo usuario
        String token = jwtUtil.generateToken(new CustomUserDetails(nuevoUsuario));

        // Devolver el token en la respuesta
        return new AuthResponse(token);
    }
}
