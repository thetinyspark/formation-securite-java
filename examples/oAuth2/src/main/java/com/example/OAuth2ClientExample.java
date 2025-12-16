package com.example;

/**
 * Client qui utilise le serveur OAuth2 simulé.
 */
public class OAuth2ClientExample {

    public static void main(String[] args) {

        MockOAuth2Server authServer = new MockOAuth2Server();

        String clientId = "my-client";
        String clientSecret = "secret123";

        System.out.println("Client ask for a token...");
        String token = authServer.getToken(clientId, clientSecret);

        if (token != null) {
            System.out.println("Received token : " + token);
        } else {
            System.out.println("Auth failure");
        }

        System.out.println("Access to protected ressource...");

        if (authServer.validateToken(token)) {
            System.out.println("Access to protected ressource with token : " + token);
        } else {
            System.out.println("Access refused");
        }
    }
}
