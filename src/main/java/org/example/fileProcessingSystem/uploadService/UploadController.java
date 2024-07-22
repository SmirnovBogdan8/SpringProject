package org.example.fileProcessingSystem.uploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.fileProcessingSystem.common.StatusService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private StatusService statusService;

    private static final long MAX_FILE_SIZE = 10485760; // 10 MB
    private static final String ALLOWED_FILE_TYPE = "text/plain";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        // Первичная валидация типа файла
        if (!file.getContentType().equals(ALLOWED_FILE_TYPE)) {
            statusService.updateStatus(fileName, "Failed primary validation: Invalid file type");
            return ResponseEntity.badRequest().body("Invalid file type");
        }

        // Первичная валидация размера файла
        if (file.getSize() > MAX_FILE_SIZE) {
            statusService.updateStatus(fileName, "Failed primary validation: File too large");
            return ResponseEntity.badRequest().body("File size exceeds the maximum limit");
        }

        // Отправка сообщения в Kafka
        kafkaTemplate.send("file-topic", fileName);

        // Запись статуса в MongoDB
        statusService.updateStatus(fileName, "File uploaded and passed primary validation");

        return ResponseEntity.ok("File uploaded successfully");
    }
}
