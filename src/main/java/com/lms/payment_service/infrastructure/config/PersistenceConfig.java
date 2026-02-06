package com.lms.payment_service.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.lms.payment_service.infrastructure.persistence.repository")
public class PersistenceConfig {
}
