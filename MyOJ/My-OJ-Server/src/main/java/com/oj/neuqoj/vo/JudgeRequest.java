package com.oj.neuqoj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JudgeRequest {
    String name;
    String[][] commandLine;
    int imageType;
    long memoryLimit;

    String containerId;
    long initTime;

    public JudgeRequest(String name, String[][] commandLine, int imageType, long memoryLimit){
        this.name = name;
        this.commandLine = commandLine;
        this.imageType = imageType;
        this.memoryLimit = memoryLimit;
    }

    public void setContainerId(String id){
        this.containerId = id;
    }

    public void setInitTime(long time){
        this.initTime = time;
    }
}
