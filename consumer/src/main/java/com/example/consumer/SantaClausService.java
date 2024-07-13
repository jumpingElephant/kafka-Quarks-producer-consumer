package com.example.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;

import java.util.logging.Logger;

@ApplicationScoped
public class SantaClausService {

    @Inject
    EntityManager entityManager;

    @Inject
    TransactionManager transactionManager;

    public static final Logger log = Logger.getLogger(SantaClausService.class.getName());

    @Transactional
    public void createGift(String giftDescription) {
        try {
            log.info("SantaClausService.createGift: " + transactionManager.getTransaction().toString());
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
        Gift gift = new Gift();
        gift.setName(giftDescription);
        entityManager.persist(gift);
    }
}