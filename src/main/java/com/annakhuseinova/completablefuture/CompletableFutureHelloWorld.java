package com.annakhuseinova.completablefuture;

import com.annakhuseinova.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.annakhuseinova.util.CommonUtil.*;

public class CompletableFutureHelloWorld {

    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld(){
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public String helloWorld_approach1(){
        String hello = helloWorldService.hello();
        String world = helloWorldService.world();
        return hello + world;
    }

    public String helloWorld_multiple_async_calls(){
        startTimer();
       CompletableFuture<String> hello = CompletableFuture.supplyAsync(()-> helloWorldService.hello());
       CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> helloWorldService.world());
       String helloWorld =  hello.thenCombine(world, (h,w)-> h + w)
               .thenApply(String::toUpperCase)
               .join();
       timeTaken();
       return helloWorld;
    }

    public String helloWorld_3_async_calls(){
        startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()-> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi, CompletableFuture!";
        });
        String helloWorld = hello.thenCombine(world, (h,w)-> h + w)
                .thenCombine(hiCompletableFuture, (previous, current)-> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_with_logging(){
        startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()-> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi, CompletableFuture!";
        });
        String helloWorld = hello.thenCombine(world, (h,w)-> h + w)
                .thenCombine(hiCompletableFuture, (previous, current)-> {
                    System.out.println("thenCombine h/w");
                    return previous + current;
                })
                .thenApply(result -> {
                    System.out.println("thenCombine previous/current");
                    return result.toUpperCase();
                })
                .join();
        timeTaken();
        return helloWorld;
    }

    public String helloWorld_3_async_calls_with_logging_async(){
        startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()-> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi, CompletableFuture!";
        });
        String helloWorld = hello.thenCombine(world, (h,w)-> h + w)
                .thenCombineAsync(hiCompletableFuture, (previous, current)-> {
                    System.out.println("thenCombine h/w");
                    return previous + current;
                })
                .thenApplyAsync(result -> {
                    System.out.println("thenCombine previous/current");
                    return result.toUpperCase();
                })
                .join();
        timeTaken();
        return helloWorld;
    }

    public CompletableFuture<String> helloWorld_thenCompose(){
        return CompletableFuture.supplyAsync(() -> helloWorldService.hello())
                .thenCompose((previous)-> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(()-> helloWorldService.helloWorld())
                .thenApply((result)-> result.toUpperCase())
                .thenAccept((result)-> {
                    System.out.println("Result is " + result);
                }).join();
        System.out.println("Done!");
        delay(2000);
    }
}
