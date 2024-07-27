package org.example.fileProcessingSystem.primaryValidationService;

import org.example.fileProcessingSystem.primaryValidationService.PrimaryValidationObject.PrimaryValidationRequest;
import org.example.fileProcessingSystem.primaryValidationService.PrimaryValidationObject.PrimaryValidationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PrimaryValidationService {

    @PostMapping("/validate")
    public PrimaryValidationResponse validateFile(@RequestBody PrimaryValidationRequest request) {
        if (request.getFileSize() > 2 * 1024 * 1024) { // 2 MB size limit
            return new PrimaryValidationResponse(false, "File size exceeds the limit of 2 MB");
        }

        if (!request.getFileType().equals("text/plain")) { // Only allow plain text files
            return new PrimaryValidationResponse(false, "Only plain text files are allowed");
        }

        return new PrimaryValidationResponse(true, "File is valid");
    }
}