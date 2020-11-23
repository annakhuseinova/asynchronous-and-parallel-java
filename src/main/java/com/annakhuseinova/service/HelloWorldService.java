package com.annakhuseinova.service;

import java.util.concurrent.CompletableFuture;

import static com.annakhuseinova.util.CommonUtil.delay;

public class HelloWorldService {

    public String helloWorld(){
        delay(1000);
        return "hello world";
    }

    public String hello(){
        delay(1000);
        return "hello";
    }

    public String world(){
        delay(1000);
        return "world";
    }

    public CompletableFuture<String> worldFuture(String input){
        return CompletableFuture.supplyAsync(()-> {
           delay(1000);
           return input + " world!";
        });
    }
}
