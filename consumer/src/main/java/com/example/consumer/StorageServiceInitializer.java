package com.example.consumer;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.serializer.reflect.ClassLoaderProvider;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.eclipse.store.storage.restservice.types.StorageRestService;
import org.eclipse.store.storage.restservice.types.StorageRestServiceResolver;

@Startup
@ApplicationScoped
public class StorageServiceInitializer {
    private EmbeddedStorageManager storage;
    private StorageRestService service;

    @PostConstruct
    public void init() {
        final MyRoot root = new MyRoot();
        storage = EmbeddedStorage.Foundation()
                .onConnectionFoundation(cf ->
                        cf.setClassLoaderProvider(ClassLoaderProvider.New(
                                Thread.currentThread().getContextClassLoader()
                        )))
                .start(root);
        storage.storeRoot();
        if (storage.root() == null) {
            storage.setRoot(new MyRoot());
            storage.storeRoot();
        }
        // StorageRestService is created only for debugging purposes
        service = StorageRestServiceResolver.resolve(storage);
        service.start();
    }

    @Produces
    public EmbeddedStorageManager produceStorageManager() {
        return storage;
    }

    @PreDestroy
    public void cleanup() {
        storage.shutdown();
        service.stop();
    }
}