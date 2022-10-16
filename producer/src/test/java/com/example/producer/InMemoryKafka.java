package com.example.producer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;

import java.util.HashMap;
import java.util.Map;

public class InMemoryKafka implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        return new HashMap<>(InMemoryConnector.switchOutgoingChannelsToInMemory("time-out"));
    }

    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}