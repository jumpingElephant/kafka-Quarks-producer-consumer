package com.example.producer;

import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.IntStream;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    @Inject
    @Channel("time-out")
    Emitter<String> timeEmitter;

    /**
     * Sends message to the "time-out" channel, can be used from a JAX-RS resource or any bean of your application.
     * Messages are sent to the broker.
     *
     * @param instant
     */
    public void sendTime(Instant instant) {
        Message<String> message = Message
                .of(instant.toString())
                .withMetadata(Metadata.of(OutgoingKafkaRecordMetadata.<String>builder()
                        .withKey(UUID.randomUUID().toString())
                        .withHeaders(new RecordHeaders().add("my-header", "value".getBytes(StandardCharsets.UTF_8)))
                        .build()));
        IntStream.range(0, 24).forEach(i -> timeEmitter.send(message));
    }

    @Scheduled(every = "1s", delayed = "3s")
    public void scheduledMethod() {
        final var currentTime = Instant.now();
        System.out.println("scheduled time: " + currentTime);
        sendTime(currentTime);
    }
}
