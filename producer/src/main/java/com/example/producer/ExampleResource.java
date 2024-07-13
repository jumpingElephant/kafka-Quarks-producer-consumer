package com.example.producer;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Instant;

@Path("/hello")
public class ExampleResource {

    @Inject
    MyReactiveMessagingApplication myReactiveMessagingApplication;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Instant now = Instant.now();
        myReactiveMessagingApplication.sendTime(now);
        System.out.println("serving hello request: " + now);
        return "Hello RESTEasy: " + now;
    }
}