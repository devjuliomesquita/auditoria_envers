package com.juliomesquita.demoauditoria.infra.configs.eventmediator.events;

public interface EventStreamProducer {

    <T> void send(String topic, T value) throws Exception;

    <T> void send(String topic, T value, String key) throws Exception;

    <T> void sendWithAckAll(String topic, T value) throws Exception;

    <T> void sendWithAckAll(String topic, T value, String key) throws Exception;
}
