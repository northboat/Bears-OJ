package com.PerformanceAnalysisSystem.test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "hahaha");
        Map<Integer, String> map1 = new HashMap<>();
        map1.putAll(map);
        map1.remove(1);
        System.out.println(map.get(1));

    }
}
