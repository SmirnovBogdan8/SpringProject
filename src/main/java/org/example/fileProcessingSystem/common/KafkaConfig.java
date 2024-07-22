package org.example.fileProcessingSystem.common;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic fileTopic() {
        return TopicBuilder.name("file-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic validationTopic() {
        return TopicBuilder.name("validation-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
