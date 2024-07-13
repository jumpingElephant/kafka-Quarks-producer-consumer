package com.example.consumer;

import org.apache.kafka.common.serialization.Deserializer;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomDeserializer implements Deserializer<Message<String>> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Initialize the deserializer if needed
    }

    @Override
    public Message<String> deserialize(String topic, byte[] data) {
        // Normally it should be like this, but needs careful exception handling.
        String payload = new String(data, StandardCharsets.UTF_8);

        // Create and return Message<String> wrapping the payload string.
        // Use org.eclipse.microprofile.reactive.messaging.Message.of static method:

        return Message.of(payload);
    }

    @Override
    public void close() {
        // Cleanup if needed
    }
}