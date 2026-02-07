package com.lms.payment_service.infrastructure.config;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaTopicInitializer implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicInitializer.class);

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
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TopicExistsException) {
                LOGGER.info("Kafka topic '{}' already exists. Skipping creation.", KafkaTopics.PAYMENT_EVENTS);
                return;
            }
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while creating Kafka topics", e);
        }
    }
}
