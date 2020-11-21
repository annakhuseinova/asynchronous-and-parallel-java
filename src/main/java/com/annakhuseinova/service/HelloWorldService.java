package com.annakhuseinova.service;

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
}
