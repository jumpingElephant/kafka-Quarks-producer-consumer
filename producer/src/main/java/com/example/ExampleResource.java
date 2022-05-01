package com.example;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/hello")
public class ExampleResource {

    @Inject
    MyReactiveMessagingApplication myReactiveMessagingApplication;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        myReactiveMessagingApplication.sendTime(Instant.now());
        System.out.println("serving hello request: " + Instant.now());
        return "Hello RESTEasy: " + Instant.now();
    }
}