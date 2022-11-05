package com.PerformanceAnalysisSystem.utils;

public class IncreaseNumber {
    static int num = 1;

    public int getNum(){
        return num;
    }

    public int getIncreaseNum(){
        return num++;
    }

    public int rebootNum(){
        int temp = num;
        num = 1;
        return temp;
    }
}
