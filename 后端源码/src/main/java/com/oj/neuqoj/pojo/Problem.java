package com.oj.neuqoj.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    int num;
    String title;
    String href;
    char level;
    String tag;
}
