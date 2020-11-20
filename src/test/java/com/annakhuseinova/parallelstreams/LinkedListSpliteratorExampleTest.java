package com.annakhuseinova.parallelstreams;

import com.annakhuseinova.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample example = new LinkedListSpliteratorExample();

    @Test
    void multiplyEachValue_in_sequential() {

        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        List<Integer> resultList = example.multiplyEachValue(inputList, 2, false);
    }

    @Test
    void multiplyEachValue_in_parallel() {

        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        List<Integer> resultList = example.multiplyEachValue(inputList, 2, true);
    }
}