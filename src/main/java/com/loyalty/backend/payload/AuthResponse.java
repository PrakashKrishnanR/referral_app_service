package com.loyalty.backend.payload;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    private String username;

    private String email;

    public AuthResponse(String accessToken, String username, String email) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
