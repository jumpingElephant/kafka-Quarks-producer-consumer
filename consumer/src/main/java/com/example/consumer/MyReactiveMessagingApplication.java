package com.example.consumer;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    @Inject
    SantaClausService santaClausService;

    @Incoming("time-in")
    public Uni<Void> timeSink(Message<List<String>> batch) {
        System.out.println("batch.getPayload().size() = " + batch.getPayload().size());
        final var now = Instant.now();
        return Multi.createFrom().iterable(batch.getPayload())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem().invoke(item -> {
                    System.out.println("[" + LocalDateTime.now() + "] received>> " + item);
                    santaClausService.createGift(item, now);
                })
                .onFailure().invoke(batch::nack)
                .onCompletion().invoke(batch::ack)
                .collect().asList()
                .map(l -> null);
    }
}