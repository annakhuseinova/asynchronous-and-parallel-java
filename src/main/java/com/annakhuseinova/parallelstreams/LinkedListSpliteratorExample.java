package com.annakhuseinova.parallelstreams;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.annakhuseinova.util.CommonUtil.startTimer;
import static com.annakhuseinova.util.CommonUtil.timeTaken;

public class LinkedListSpliteratorExample {

    public List<Integer> multiplyEachValue(LinkedList<Integer> inputList, int multiplyValue, boolean isParallel){
        Stream<Integer> integerStream = inputList.stream();

        startTimer();
        if (isParallel) integerStream.parallel();
        List<Integer> resultList = integerStream.map(integer -> integer * multiplyValue)
                .collect(Collectors.toList());
        timeTaken();
        return resultList;
    }
}
