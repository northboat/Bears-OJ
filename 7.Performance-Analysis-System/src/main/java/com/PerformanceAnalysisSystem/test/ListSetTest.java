package com.PerformanceAnalysisSystem.test;

import java.util.ArrayList;
import java.util.List;

public class ListSetTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.set(0, 4);
        System.out.println(list);
    }
}
