package com.oj.neuqoj.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Comment {
    int question;
    long floor;
    String from;
    long to;
    String content;

    List<Comment> comments;

    public Comment(int question, String from, long to, String content) {
        this.question = question;
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public Comment(int question, long floor, String from, long to, String content) {
        this.question = question;
        this.floor = floor;
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public void setComments(List<Comment> comments){
        if(this.to != 0){
            return;
        }
        this.comments = new ArrayList<>();
        for(Comment c: comments){
            if(c.getTo() == this.floor){
                this.comments.add(c);
            }
        }
    }
}
