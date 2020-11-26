package com.annakhuseinova.completablefuture;

import com.annakhuseinova.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    // Make these three calls but take result from the one that receives the result faster. The slower responses will be
    // abandoned automatically.
    public String anyOf(){
        //db
        CompletableFuture<String> dbCall = CompletableFuture.supplyAsync(()-> {
            delay(1000);
            System.out.println("Response from db");
            return "Hello, World";
        });
        // restCall
        CompletableFuture<String> restCall = CompletableFuture.supplyAsync(()-> {
            delay(2000);
            System.out.println("Response from rest service");
            return "Hello, World";
        });
        // soapCall
        CompletableFuture<String> soapCall = CompletableFuture.supplyAsync(()-> {
            delay(3000);
            System.out.println("Response from soap service");
            return "Hello, World";
        });
        List<CompletableFuture<String>> completableFutureList = List.of(dbCall, restCall, soapCall);
        CompletableFuture<Object> cfAnyOf =
                CompletableFuture.anyOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]));
        String result = (String) cfAnyOf.thenApply(v -> {
           if (v instanceof String){
               return v;
           }
           return null;
        }).join();
        return result;
    }

    public String helloWorld_3_async_calls_custom_threadpool_async(){
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()-> this.helloWorldService.hello(), executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> this.helloWorldService.world(), executorService);

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()-> {
           delay(1000);
           return "Hi, CompletableFuture!";
        }, executorService);

        String hw = hello.thenCombineAsync(world, (h,w)-> {
            System.out.println("thenCombine h/w");
            return h + w;
        }, executorService)
                .thenCombineAsync(hiCompletableFuture, (previous, current)-> {
                    System.out.println("the Combine, previous/current");
                    return previous + current;
                }, executorService).thenApply(s -> {
                    System.out.println("Then apply");
                    return s.toUpperCase();
                }).join();
        return hw;
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
