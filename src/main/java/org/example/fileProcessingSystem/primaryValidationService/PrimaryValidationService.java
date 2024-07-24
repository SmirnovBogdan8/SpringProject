package org.example.fileProcessingSystem.primaryValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.example.fileProcessingSystem.common.StatusService;

@Service
public class PrimaryValidationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private StatusService statusService;

    @KafkaListener(topics = "file-topic", groupId = "primary-validation-group")
    public void listen(String fileName) {
        // Логика первичной валидации (например, проверка формата имени файла)
        boolean isValid = performPrimaryValidation(fileName);

        if (isValid) {
            // Отправка сообщения в Kafka о результате валидации
            kafkaTemplate.send("validation-topic", fileName);
            // Обновление статуса в MongoDB
            statusService.updateStatus(fileName, "Passed primary validation");
        } else {
            // Обновление статуса в MongoDB
            statusService.updateStatus(fileName, "Failed primary validation");
        }
    }

    private boolean performPrimaryValidation(String fileName) {
        // Пример логики валидации: файл должен иметь расширение .txt
        return fileName != null && fileName.endsWith(".txt");
    }
}