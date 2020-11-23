package com.annakhuseinova.scheduledexecutorservice;

import java.util.concurrent.*;

public class Example {

    public static void main(String[] args) {
        // Here it is specified that the Callable should be executed after 5 seconds
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("Executed!");
                return "Called";
            }
        }, 5, TimeUnit.SECONDS);
    }
}
