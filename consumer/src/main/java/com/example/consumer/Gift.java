package com.example.consumer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import java.time.Instant;

@Entity
public class Gift {
    private Long id;
    private String name;
    private String thread;
    private Instant instant;

    @Id
    @SequenceGenerator(name = "giftSeq", sequenceName = "gift_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "giftSeq")
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