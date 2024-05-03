package com.example.mindmingle.entities;
public class AuthenticationResponse {

    private String token;
    private final String usernameOfToken;

    public AuthenticationResponse(String token, String usernameOfToken) {
        this.token = token;
        this.usernameOfToken = usernameOfToken;
    }

    public String getToken() {
        return token;
    }

    public String getUsernameOfToken() {
        return usernameOfToken;
    }
}
