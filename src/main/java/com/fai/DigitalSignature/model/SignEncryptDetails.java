package com.fai.DigitalSignature.model;

import java.util.List;

public class SignEncryptDetails {
    String userId;
    String signature;
    List<EncryptionDetails> encryptionDetails;
    String docHash;
    String encryptedData;

    public SignEncryptDetails() {
    }

    public SignEncryptDetails(String userId, String signature, List<EncryptionDetails> encryptionDetails, String docHash, String encryptedData) {
        this.userId = userId;
        this.signature = signature;
        this.encryptionDetails = encryptionDetails;
        this.docHash = docHash;
        this.encryptedData = encryptedData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<EncryptionDetails> getEncryptionDetails() {
        return encryptionDetails;
    }

    public void setEncryptionDetails(List<EncryptionDetails> encryptionDetails) {
        this.encryptionDetails = encryptionDetails;
    }

    public String getDocHash() {
        return docHash;
    }

    public void setDocHash(String docHash) {
        this.docHash = docHash;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
