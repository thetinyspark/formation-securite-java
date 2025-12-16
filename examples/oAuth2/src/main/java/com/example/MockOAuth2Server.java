package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Serveur OAuth2 simulé.
 */
public class MockOAuth2Server {

    private Map<String, String> validClients = new HashMap<>();

    public MockOAuth2Server() {
        // client_id -> client_secret
        validClients.put("my-client", "secret123");
    }

    /**
     * Valide le client et retourne un token.
     */
    public String getToken(String clientId, String clientSecret) {
        String expectedSecret = validClients.get(clientId);
        if (expectedSecret != null && expectedSecret.equals(clientSecret)) {
            // Génère un token aléatoire
            return UUID.randomUUID().toString();
        }
        return null; // Auth échouée
    }

    /**
     * Vérifie le token pour accéder à la ressource.
     */
    public boolean validateToken(String token) {
        // Ici, tout token non nul est considéré valide
        return token != null && !token.isEmpty();
    }
}
