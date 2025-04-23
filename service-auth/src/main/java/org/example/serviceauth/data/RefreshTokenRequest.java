package org.example.serviceauth.data;

public class RefreshTokenRequest {
    private String refreshToken;

    // Default constructor (needed for JSON deserialization)
    public RefreshTokenRequest() {}

    // Constructor with parameter
    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getter
    public String getRefreshToken() {
        return refreshToken;
    }

    // Setter
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

