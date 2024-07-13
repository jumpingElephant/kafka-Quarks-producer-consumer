package com.example.consumer;

import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    @Inject
    SantaClausService santaClausService;

    @Incoming("time-in")
    public CompletionStage<Void> timeSink(List<Message<String>> messages) {
        return CompletableFuture.runAsync(() -> {
            for (Message<String> timeMessage : messages) {
                String key = null;
                if (timeMessage.getMetadata() != null) {
                    Metadata metadata = timeMessage.getMetadata();
                    key = String.valueOf(metadata.get(IncomingKafkaRecordMetadata.class));
                }
                if (timeMessage.getMetadata(IncomingKafkaRecordMetadata.class).isPresent()) {
                    final Optional<IncomingKafkaRecordMetadata> metadata = timeMessage.getMetadata(IncomingKafkaRecordMetadata.class);
                    IncomingKafkaRecordMetadata<String, Object> kafkaMetadata = metadata.get();
                    String kafkaKey = kafkaMetadata.getKey();
                    String kafkaTopic = kafkaMetadata.getTopic();
                    key = kafkaKey + "-" + kafkaTopic;
                }
                // Your message processing for this payload
                System.out.println("[" + LocalDateTime.now() + "] received>> " + timeMessage.getPayload() + " key: " + key);
                santaClausService.createGift(timeMessage.getPayload());
            }
        });
    }
}
