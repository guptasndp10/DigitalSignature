package com.fai.DigitalSignature.model;

import java.util.Arrays;
import java.util.List;

public class UserSignForm {
    private String userId;
    private List<String> counterUserList;
    private byte[] fileData;

    public UserSignForm() {
    }

    public UserSignForm(String userId, List<String> counterUserList, byte[] fileData) {
        this.userId = userId;
        this.counterUserList = counterUserList;
        this.fileData = fileData;
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

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return "UserSignForm{" +
                "userId='" + userId + '\'' +
                ", counterUserList=" + counterUserList +
                ", fileData=" + Arrays.toString(fileData) +
                '}';
    }
}
