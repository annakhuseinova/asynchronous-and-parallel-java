package com.annakhuseinova.completablefuture;

import com.annakhuseinova.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletableFutureHelloWorldTest {

    private HelloWorldService helloWorldService = new HelloWorldService();
    private CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();
        completableFuture.thenAccept(result -> {
           assertEquals("HELLO WORLD", result);
        });
    }

    @Test
    void helloWorld_multiple_async_calls() {
        String helloWorld = completableFutureHelloWorld.helloWorld_multiple_async_calls();
        assertEquals("HELLO WORLD!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls() {
        String helloWorld = completableFutureHelloWorld.helloWorld_3_async_calls();
        assertEquals("HELLO WORLD! HI, COMPLETABLE FUTURE!", helloWorld);
    }
}