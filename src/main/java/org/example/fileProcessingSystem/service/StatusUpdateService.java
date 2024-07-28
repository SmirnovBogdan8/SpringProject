package org.example.fileProcessingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.example.fileProcessingSystem.common.StatusService;

@Service
public class StatusUpdateService {

    @Autowired
    private StatusService statusService;

    @KafkaListener(topics = "status-update-topic", groupId = "status-update-group")
    public void listen(String message) {
        // Логика обработки сообщения и обновления статуса в MongoDB
        // Здесь message - это строка, содержащая информацию о файле и его новом статусе
        String[] parts = message.split(":");
        if (parts.length == 2) {
            String fileName = parts[0];
            String status = parts[1];
            statusService.updateStatus(fileName, status);
        }
    }
}
