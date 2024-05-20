package com.oj.neuqoj.service.impl;

import com.oj.neuqoj.docker.impl.DockerRunner;
import com.oj.neuqoj.service.DockerService;
import com.oj.neuqoj.utils.ResultCode;
import com.oj.neuqoj.utils.ResultUtil;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class DockerServiceImpl implements DockerService {

    private Deque<DockerRunner> rest;
    private Deque<DockerRunner> running;


    private Deque<Map<String, String>> blockingQueue;

    public void init(){
        running = new LinkedList<>();
        blockingQueue = new LinkedList<>();
    }

    @Override
    public Map<String, Object> judge(Map<String, String> params) {
        //若为空，请求入阻塞队列
        if(rest.isEmpty()){
            blockingQueue.offer(params);
        }

        //更新队列
        DockerRunner cur = rest.poll();
        running.offer(cur);

        // 新建线程判题
        FutureTask<Map<String, Object>> task = new FutureTask<>(cur);
        new Thread(task).start();
        if(!task.isDone()){ System.out.println("The task is running"); }
        //获取结果并返回
        Map<String, Object> res = null;
        try {
            res = task.get();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return res;
    }


    public void waiting(){

    }
}
