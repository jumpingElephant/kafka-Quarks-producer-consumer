package com.example;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    /**
     * Consume the message from the "time-in" channel.
     * Messages come from the broker.
     **/
    @Incoming("time-in")
    public void timeSink(String time) {
        System.out.println("t>> " + time);
    }
}
