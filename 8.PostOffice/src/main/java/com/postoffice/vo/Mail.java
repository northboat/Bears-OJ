package com.postoffice.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {
    private int num;
    private int count;
    private String name;
    private String from;
    private String to;
    private String subject;
    private String text;

    public Mail(String name, String to, String subject, String text) {
        this.name = name;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}
