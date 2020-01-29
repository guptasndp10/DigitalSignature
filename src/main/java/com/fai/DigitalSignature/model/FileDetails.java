package com.fai.DigitalSignature.model;

import java.util.Arrays;

public class FileDetails {
    private byte[] fileData;
    private boolean fileDataIntegrity;

    public FileDetails(){}

    public FileDetails(byte[] fileData) { this.fileData = fileData; }

    public FileDetails(byte[] fileData, boolean fileDataIntegrity) {
        this.fileData = fileData;
        this.fileDataIntegrity = fileDataIntegrity;
    }

    public byte[] getFileData() { return fileData; }

    public void setFileData(byte[] fileData) { this.fileData = fileData; }

    public boolean isFileDataIntegrity() { return fileDataIntegrity; }

    public void setFileDataIntegrity(boolean fileDataIntegrity) { this.fileDataIntegrity = fileDataIntegrity; }

    @Override
    public String toString() {
        return "FileDetails{" +
                "fileData=" + Arrays.toString(fileData) +
                ", fileDataIntegrity=" + fileDataIntegrity +
                '}';
    }
}
