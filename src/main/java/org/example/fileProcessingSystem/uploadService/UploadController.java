package org.example.fileProcessingSystem.uploadService;

import org.example.fileProcessingSystem.uploadService.PrimaryValidationObject.PrimaryValidationRequest;
import org.example.fileProcessingSystem.uploadService.PrimaryValidationObject.PrimaryValidationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    @Value("${primary.validation.url}")
    private String primaryValidationUrl;

    @GetMapping("/upload")
    public String listUploadedFiles(Model model) {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/upload";
        }

        RestTemplate restTemplate = new RestTemplate();
        PrimaryValidationRequest request = new PrimaryValidationRequest(file.getOriginalFilename(), file.getSize(), file.getContentType());
        PrimaryValidationResponse response = restTemplate.postForObject(primaryValidationUrl, request, PrimaryValidationResponse.class);

        if (response.isValid()) {
            redirectAttributes.addFlashAttribute("message",
                    "File '" + file.getOriginalFilename() + "' uploaded successfully: " + response.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("message",
                    "File '" + file.getOriginalFilename() + "' validation failed: " + response.getMessage());
        }

        return "redirect:/upload";
    }
}
