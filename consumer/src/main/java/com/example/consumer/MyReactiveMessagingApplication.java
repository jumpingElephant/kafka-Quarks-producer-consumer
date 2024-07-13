package com.example.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    @Inject
    SantaClausService santaClausService;

    /**
     * Consume the message from the "time-in" channel.
     * Messages come from the broker.
     **/
    @Incoming("time-in")
    public void timeSink(String time) {
        System.out.println("[" + LocalDateTime.now() + "] received>> " + time);
        santaClausService.createGift(time);
    }
}
