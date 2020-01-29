package com.fai.DigitalSignature.model;

public class EncryptionDetails {
    private String userId;
    private String secretKey;

    public EncryptionDetails(String userId, String secretKey) {
        this.userId = userId;
        this.secretKey = secretKey;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getSecretKey() { return secretKey; }

    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
}
