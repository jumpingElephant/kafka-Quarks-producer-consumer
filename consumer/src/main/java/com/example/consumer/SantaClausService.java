package com.example.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

import java.time.Instant;
import java.util.logging.Logger;

@ApplicationScoped
public class SantaClausService {

    public static final Logger log = Logger.getLogger(SantaClausService.class.getName());

    @Inject
    EmbeddedStorageManager storage;

    public void createGift(String giftDescription, Instant instant) {
        if (storage.root() instanceof MyRoot root) {
            Gift gift = new Gift();
            gift.setName(giftDescription);
            gift.setThread(String.format("%s/%s(%d)", Thread.currentThread().getThreadGroup().getName(), Thread.currentThread().getName(), Thread.currentThread().getId()));
            gift.setInstant(instant);
            root.myObjects.add(gift);
            storage.store(root.myObjects);
        }
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}