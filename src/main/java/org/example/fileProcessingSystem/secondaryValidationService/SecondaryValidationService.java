package org.example.fileProcessingSystem.secondaryValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.example.fileProcessingSystem.common.StatusService;

@Service
public class SecondaryValidationService {

    @Autowired
    private StatusService statusService;

    @KafkaListener(topics = "validation-topic", groupId = "secondary-validation-group")
    public void listen(String fileName) {
        // Логика вторичной валидации (например, проверка содержимого файла)
        boolean isValid = performSecondaryValidation(fileName);

        if (isValid) {
            // Обновление статуса в MongoDB
            statusService.updateStatus(fileName, "Passed secondary validation");
        } else {
            // Обновление статуса в MongoDB
            statusService.updateStatus(fileName, "Failed secondary validation");
        }
    }

    private boolean performSecondaryValidation(String fileName) {
        // Пример логики валидации: содержимое файла должно удовлетворять определенным условиям
        // Здесь мы предполагаем, что файл уже загружен и доступен для чтения
        // Логика проверки содержимого файла
        return true; // Замените на реальную логику проверки содержимого
    }
}
