package com.example.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.Instant;

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
    void sendTime(Instant instant) {
        timeEmitter.send(instant.toString());
    }
}
