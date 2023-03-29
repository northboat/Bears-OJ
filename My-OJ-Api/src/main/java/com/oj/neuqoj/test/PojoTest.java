package com.oj.neuqoj.test;

import com.oj.neuqoj.pojo.Info;

public class PojoTest {

    @org.junit.Test
    public void infoTest(){
        Info info = new Info("da", "dsa", "das", 8);
//        info.finished_index = "00000";
//        info.pass(5, 20800);
        System.out.println(info.getFinished_index());
        System.out.println(info.hasPassed(7));
    }
}
