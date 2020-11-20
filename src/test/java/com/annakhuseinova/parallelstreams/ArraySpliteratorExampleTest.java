package com.annakhuseinova.parallelstreams;

import com.annakhuseinova.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArraySpliteratorExampleTest {

    private ArraySpliteratorExample example = new ArraySpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValue_in_sequential_stream() {

        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);
        List<Integer> resultList = example.multiplyEachValue(inputList, 2, false);
        assertEquals(size, resultList.size());
    }

    @RepeatedTest(5)
    void multiplyEachValue_in_parallel_stream() {

        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);
        List<Integer> resultList = example.multiplyEachValue(inputList, 2, true);
        assertEquals(size, resultList.size());
    }
}