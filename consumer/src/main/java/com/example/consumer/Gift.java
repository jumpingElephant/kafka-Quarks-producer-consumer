package com.example.consumer;

import java.time.Instant;

public class Gift {
    private Long id;
    private String name;
    private String thread;
    private Instant instant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public Instant getInstant() {
        return instant;
    }

    public Gift setInstant(Instant instant) {
        this.instant = instant;
        return this;
    }
}