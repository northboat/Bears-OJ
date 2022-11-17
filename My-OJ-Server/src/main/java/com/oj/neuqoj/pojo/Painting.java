package com.oj.neuqoj.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Painting {
    int num;
    String title;
    String desc;
    long thumb;
    String from;
    String content;

    public Painting(String title, String desc, String from, String content){
        this.title = title;
        this.desc = desc;
        this.from = from;
        this.content = content;
    }
}
