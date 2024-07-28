package org.example.fileProcessingSystem.controller;

import org.example.fileProcessingSystem.service.PrimaryValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final PrimaryValidationService primaryValidationService;

    public UploadController(PrimaryValidationService primaryValidationService) {
        this.primaryValidationService = primaryValidationService;
    }

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

        try {
            String validationMessage = primaryValidationService.validateFile(file);
            if (validationMessage != null) {
                redirectAttributes.addFlashAttribute("message", "Validation failed: " + validationMessage);
                return "redirect:/upload";
            }

            // Сохраняем файл, если валидация прошла успешно
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + "/" + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "An error occurred while uploading the file: " + e.getMessage());
        }

        return "redirect:/upload";
    }
}
