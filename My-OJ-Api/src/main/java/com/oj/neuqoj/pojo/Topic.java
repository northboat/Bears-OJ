package com.oj.neuqoj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Topic {
    int num;
    String title;
    String desc;
    String content;
    String tag;
    String from;
    String contact;
    long replies;

    public Topic(String title, String desc, String from, String contact, String tag, String content){
        this.title = title;
        this.desc = desc;
        this.from = from;
        this.contact = contact;
        this.tag = tag;
        this.content = content;
    }
}
