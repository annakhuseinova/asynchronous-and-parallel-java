package com.annakhuseinova.parallelstreams;

import com.annakhuseinova.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.annakhuseinova.util.CommonUtil.*;

public class ParallelStreamsExample {

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStreamsExample example = new ParallelStreamsExample();
        startTimer();
        List<String> result = example.stringTransform(namesList);
        timeTaken();
    }

    public List<String> stringTransform(List<String> namesList){
        return namesList.parallelStream().map(this::addNameLengthTransform).collect(Collectors.toList());
    }

    public List<String> stringTransform_1(List<String> namesList, boolean isParallel){
        Stream<String> namesStream = namesList.stream();
        if (isParallel)
            namesStream.parallel();

        return namesStream.map(this::addNameLengthTransform).collect(Collectors.toList());
    }

    private String addNameLengthTransform(String name){
        delay(500);
        return name.length() + " - " + name;
    }
}
