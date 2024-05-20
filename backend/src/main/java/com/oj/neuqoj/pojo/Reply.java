package com.oj.neuqoj.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Reply {
    int topic;
    long floor;
    long to;
    String from;
    String content;

    List<Reply> replies;

    public Reply(int topic, long to, String from, String content) {
        this.topic = topic;
        this.to = to;
        this.from = from;
        this.content = content;
    }

    public Reply(int topic, long floor, long to, String from, String content) {
        this.topic = topic;
        this.floor = floor;
        this.to = to;
        this.from = from;
        this.content = content;
    }

    public void setReplies(List<Reply> replies){
        if(this.to != 0){
            return;
        }
        this.replies = new ArrayList<>();
        for(Reply r: replies){
            if(r.getTo() == this.floor){
                this.replies.add(r);
            }
        }
    }
}
