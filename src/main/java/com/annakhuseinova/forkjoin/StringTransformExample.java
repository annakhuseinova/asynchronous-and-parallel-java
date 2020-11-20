package com.annakhuseinova.forkjoin;

import com.annakhuseinova.util.DataSet;

import java.util.ArrayList;
import java.util.List;

import static com.annakhuseinova.util.CommonUtil.delay;
import static com.annakhuseinova.util.CommonUtil.stopWatch;

public class StringTransformExample {

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();

        names.forEach((name)->{
            String newValue = addNameLengthTransform(name);
            resultList.add(newValue);
        });
        stopWatch.stop();

    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+ name ;
    }
}
