package com.fai.DigitalSignature.model;

import java.util.Arrays;
import java.util.List;

public class User {
    private String userId;
    private String userName;
    private List<String> counterUserList;
    private String fileId;
    private String fileName;
    private byte[] fileData;
    private String  signature;
    private String secretKey;
    private String encryptedData;
    private String hash;

    public User(String userId, String userName, List<String> counterUserList, String fileId, String fileName, byte[] fileData, String signature, String secretKey, String encryptedData, String hash) {
        this.userId = userId;
        this.userName = userName;
        this.counterUserList = counterUserList;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileData = fileData;
        this.signature = signature;
        this.secretKey = secretKey;
        this.encryptedData = encryptedData;
        this.hash = hash;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getCounterUserList() {
        return counterUserList;
    }

    public void setCounterUserList(List<String> counterUserList) {
        this.counterUserList = counterUserList;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", counterUserList=" + counterUserList +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileData=" + Arrays.toString(fileData) +
                ", signature='" + signature + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", encryptedData='" + encryptedData + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
