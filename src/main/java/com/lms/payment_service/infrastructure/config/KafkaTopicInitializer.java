package com.lms.payment_service.infrastructure.config;

import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicInitializer implements ApplicationRunner {

    private final String bootstrapServers;

    public KafkaTopicInitializer(@Value("${spring.kafka.bootstrap-servers:localhost:9092}") String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient client = AdminClient.create(props)) {
            List<NewTopic> topics = List.of(new NewTopic(KafkaTopics.PAYMENT_EVENTS, 3, (short) 1));
            client.createTopics(topics).all().get();
        }
    }
}
