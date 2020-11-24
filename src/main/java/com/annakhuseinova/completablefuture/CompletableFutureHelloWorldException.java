package com.annakhuseinova.completablefuture;

import com.annakhuseinova.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.annakhuseinova.util.CommonUtil.*;

public class CompletableFutureHelloWorldException {

    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorld_3_async_calls_handle_exception() {
        startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi, CompletableFuture!";
        });
        String helloWorld = hello.handle((result, exception)-> {
            System.out.println("Exception is :" + exception.getMessage());
            return "";
        })
                .thenCombine(world, (h, w) -> h + w)
                .handle((result, exception)-> {
                    System.out.println(exception.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_handle_exception_exceptionally() {
        startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi, CompletableFuture!";
        });
        String helloWorld = hello
                .exceptionally((exception)-> {
            System.out.println("Exception is :" + exception.getMessage());
            return "";
        })
                .thenCombine(world, (h, w) -> h + w)
                .exceptionally((exception)-> {
                    System.out.println(exception.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_handle_exception_whenComplete() {
        startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi, CompletableFuture!";
        });
        String helloWorld = hello
                .whenComplete((result, exception)-> {
                    System.out.println("Exception is :" + exception.getMessage());
                })
                .thenCombine(world, (h, w) -> h + w)
                .whenComplete((result, exception)-> {
                    System.out.println(exception.getMessage());
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return helloWorld;
    }
}
