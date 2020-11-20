package com.annakhuseinova.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.annakhuseinova.util.CommonUtil.delay;
import static com.annakhuseinova.util.DataSet.namesList;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(namesList());
        forkJoinPool.invoke(forkJoinUsingRecursion);
    }

    @Override
    protected List<String> compute() {

        if (inputList.size() <= 1){
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(name));
            return resultList;
        }
        int midpoint = inputList.size()/2;
        // fork method allows to asynchronously add a task to a worker thread queue.
        ForkJoinTask<List<String>> leftInputList =  new ForkJoinUsingRecursion(inputList.subList(0, midpoint)).fork();
        inputList = inputList.subList(midpoint, inputList.size());
        List<String> rightResult = compute();
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }

    private static String addNameLengthTransform(String name){
        delay(500);
        return name.length() + " - " + name;
    }
}
