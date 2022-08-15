package com.oj.neuqoj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    int num;
    String title;
    char level;
    int thumb;
    int example;
    String name;
    String func;
    String tag;
}
