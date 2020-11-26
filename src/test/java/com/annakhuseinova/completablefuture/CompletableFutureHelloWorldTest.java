package com.annakhuseinova.completablefuture;

import com.annakhuseinova.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.annakhuseinova.util.CommonUtil.startTimer;
import static com.annakhuseinova.util.CommonUtil.timeTaken;
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

    @Test
    void helloWorld_3_async_calls_logging() {
        String helloWorld = completableFutureHelloWorld.helloWorld_3_async_calls();
        assertEquals("HELLO WORLD! HI, COMPLETABLE FUTURE!", helloWorld);
    }

    @Test
    void helloWorld_thenCompose() {
        startTimer();
        CompletableFuture<String> completableFuture =  completableFutureHelloWorld.helloWorld_thenCompose();
        completableFuture.thenAccept(s -> {
            assertEquals("HELLO WORLD!", s);
        }).join();

        timeTaken();
    }

    @Test
    void anyOf() {
        startTimer();
        String result = completableFutureHelloWorld.anyOf();
        timeTaken();
        assertEquals("Hello, World!", result);
    }
}