package com.fabrik.api.common.config;

import org.ff4j.FF4j;
import org.ff4j.audit.repository.InMemoryEventRepository;
import org.ff4j.property.store.InMemoryPropertyStore;
import org.ff4j.store.InMemoryFeatureStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4JConfig {

    /**
     * Provides an instance of FF4j (Feature Flipper for Java) configured with default in-memory stores.
     *
     * @return An instance of FF4j configured with default in-memory stores.
     */
    @Bean
    public FF4j getFF4j() {
        FF4j ff4j = new FF4j();

        ff4j.setFeatureStore(new InMemoryFeatureStore());
        ff4j.setPropertiesStore(new InMemoryPropertyStore());
        ff4j.setEventRepository(new InMemoryEventRepository());
        ff4j.audit(true);
        ff4j.autoCreate(true);
        return ff4j;
    }
}