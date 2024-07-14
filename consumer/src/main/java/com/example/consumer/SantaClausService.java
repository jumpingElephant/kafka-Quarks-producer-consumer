package com.example.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.logging.Logger;

@ApplicationScoped
public class SantaClausService {

    @Inject
    EntityManager entityManager;

    @Inject
    TransactionManager transactionManager;

    public static final Logger log = Logger.getLogger(SantaClausService.class.getName());

    @Transactional
    public void createGift(String giftDescription, Instant instant) {
        try {
            log.info("SantaClausService.createGift: " + transactionManager.getTransaction().toString());
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
        Gift gift = new Gift();
        gift.setName(giftDescription);
        gift.setThread(String.format("%s/%s(%d)", Thread.currentThread().getThreadGroup().getName(), Thread.currentThread().getName(), Thread.currentThread().getId()));
        gift.setInstant(instant);
        entityManager.persist(gift);
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}