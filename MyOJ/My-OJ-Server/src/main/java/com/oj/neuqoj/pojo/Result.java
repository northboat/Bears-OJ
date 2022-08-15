package com.oj.neuqoj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    String account;
    int num;
    String title;
    String key1;
    String key2;
    String key3;
    String key4;
    String val1;
    String val2;
    String val3;
    String val4;
    String code;

    public Result(String account, int num){
        this.account = account;
        this.num = num;
        title = "test";
        key1 = "test";
        key2 = "test";
        key3 = "test";
        key4 = "test";
        val1 = "test";
        val2 = "test";
        val3 = "test";
        val4 = "test";
        code = "test";
    }
}
