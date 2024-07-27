package org.example.fileProcessingSystem.uploadService.PrimaryValidationObject;

public class PrimaryValidationRequest {
    private String fileName;
    private long fileSize;
    private String fileType;

    public PrimaryValidationRequest(String fileName, long fileSize, String fileType) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    // getters and setters
}