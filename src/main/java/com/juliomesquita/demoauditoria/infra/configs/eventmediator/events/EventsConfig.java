package com.juliomesquita.demoauditoria.infra.configs.eventmediator.events;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class EventsConfig {

    @Bean
    public EventStreamProducer eventStreamProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            @Qualifier("allAcksKafkaTemplate") KafkaTemplate<String, String> allAcksKafkaTemplate
    ) {
        return new EventStreamProducerImpl(kafkaTemplate, allAcksKafkaTemplate);
    }
}
