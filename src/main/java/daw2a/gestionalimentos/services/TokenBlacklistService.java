package daw2a.gestionalimentos.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service that manages the blacklisted tokens.
 */
@Service
public class TokenBlacklistService {
    private final Set<String> blacklistedTokens = new HashSet<>();

    /**
     * Adds a token to the blacklist.
     *
     * @param token The token to add.
     */
    public void addTokenToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * Removes a token from the blacklist.
     *
     * @param token The token to remove.
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
