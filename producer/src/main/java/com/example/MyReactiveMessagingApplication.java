package com.example;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
