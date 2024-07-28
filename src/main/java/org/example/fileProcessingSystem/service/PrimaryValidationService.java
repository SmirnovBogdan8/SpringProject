package org.example.fileProcessingSystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PrimaryValidationService {

    public String validateFile(MultipartFile file) {
        // Проверка типа файла (например, только текстовые файлы)
        if (!file.getContentType().equals("text/plain")) {
            return "Only plain text files are allowed";
        }

        // Проверка размера файла (например, не более 2 MB)
        if (file.getSize() > 2 * 1024 * 1024) {
            return "File size exceeds the limit of 2 MB";
        }

        // Если файл проходит все проверки, возвращаем null (нет ошибок)
        return null;
    }
}