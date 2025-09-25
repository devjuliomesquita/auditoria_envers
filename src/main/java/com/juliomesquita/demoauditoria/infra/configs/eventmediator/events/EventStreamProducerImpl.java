package com.juliomesquita.demoauditoria.infra.configs.eventmediator.events;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public class EventStreamProducerImpl implements EventStreamProducer {

    private static final Logger log = LoggerFactory.getLogger(EventStreamProducerImpl.class);
    private final KafkaTemplate<String, String> defaultKafkaTemplate;
    private final KafkaTemplate<String, String> allAcksKafkaTemplate;

    ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public EventStreamProducerImpl(
        KafkaTemplate<String, String> defaultKafkaTemplate,
        KafkaTemplate<String, String> allAcksKafkaTemplate
    ) {
        this.defaultKafkaTemplate = defaultKafkaTemplate;
        this.allAcksKafkaTemplate = allAcksKafkaTemplate;
    }

    @Override
    public <T> void send(String topic, T value) throws Exception {
        try {
            final var stringfiedValue = mapper.writeValueAsString(value);
            final String key = UUID.randomUUID().toString();

            this.defaultKafkaTemplate.send(
                topic,
                key,
                stringfiedValue
            );
            log.debug("Enviando evento para o tópico {}: {}", topic, key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar evento: " + e.getMessage());
        }
    }

    @Override
    public <T> void send(String topic, T value, String key) throws Exception {
        try {
            final var stringfiedValue = mapper.writeValueAsString(value);
            final String keyToSend = key != null ? key : UUID.randomUUID().toString();

            this.defaultKafkaTemplate.send(
                topic,
                keyToSend,
                stringfiedValue
            );
            log.debug("Enviando evento para o tópico {}: {}", topic, key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar evento: " + e.getMessage());
        }
    }

    @Override
    public <T> void sendWithAckAll(String topic, T value) throws Exception {
        try {
            final var stringfiedValue = mapper.writeValueAsString(value);
            final String key = UUID.randomUUID().toString();

            this.allAcksKafkaTemplate.send(
                topic,
                key,
                stringfiedValue
            );
            log.debug("Enviando evento para o tópico {}: {}", topic, key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar evento (with ack all): " + e.getMessage());
        }
    }

    @Override
    public <T> void sendWithAckAll(String topic, T value, String key) throws Exception {
        try {
            final var stringfiedValue = mapper.writeValueAsString(value);
            final String keyToSend = key != null ? key : UUID.randomUUID().toString();

            this.allAcksKafkaTemplate.send(
                topic,
                keyToSend,
                stringfiedValue
            );
            log.debug("Enviando evento para o tópico {}: key: {}", topic, key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar evento (with ack all): " + e.getMessage());
        }
    }

}
