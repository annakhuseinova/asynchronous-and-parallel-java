package com.annakhuseinova.util;

import org.apache.commons.lang3.time.StopWatch;

import static java.lang.Thread.sleep;

public class CommonUtil {

    public static StopWatch stopWatch = new StopWatch();

    public static void delay(long delayMilliSeconds){
        try {
            sleep(delayMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startTimer(){
        stopWatch.reset();
        stopWatch.start();
    }

    public static void timeTaken(){
        stopWatch.stop();
        System.out.println("Total Time Taken: " + stopWatch.getTime());
    }
}
