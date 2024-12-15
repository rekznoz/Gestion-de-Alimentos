package daw2a.gestionalimentos.security.dto;

import lombok.Data;

/**
 * AuthResponse
 */
@Data
public class AuthResponse {

    private String token;

    /**
     * Default constructor
     */
    public AuthResponse() {
    }

    /**
     * Constructor with token
     *
     * @param token The token
     */
    public AuthResponse(String token) {
        this.token = token;
    }

}