package com.oj.neuqoj.api;


import com.oj.neuqoj.mapper.ProblemMapper;
import com.oj.neuqoj.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProblemApi {

    private ProblemMapper problemMapper;
    @Autowired
    public void setProblemMapper(ProblemMapper problemMapper){
        this.problemMapper = problemMapper;
    }

    @RequestMapping("/getList")
    public ResultUtil getList(){
        return ResultUtil.success(problemMapper.getList());
    }
}
