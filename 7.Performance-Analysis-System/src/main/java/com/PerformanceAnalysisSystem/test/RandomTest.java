package com.PerformanceAnalysisSystem.test;

public class RandomTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int j = (int)(Math.random()*3+1);
            System.out.println(j);
        }
    }
}
