package com.oj.neuqoj.api;

import com.oj.neuqoj.mapper.NewsMapper;
import com.oj.neuqoj.pojo.News;
import com.oj.neuqoj.utils.ResultCode;
import com.oj.neuqoj.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class NewsApi {

    public static int version = 1;

    public void setVersion(int version){
        NewsApi.version = version;
    }

    private NewsMapper newsMapper;
    @Autowired
    public void setNewsMapper(NewsMapper newsMapper){
        this.newsMapper = newsMapper;
    }

    @RequestMapping("/getNews")
    public ResultUtil getNews(){
        News news = newsMapper.getNews(version);
        if(news == null){
            return ResultUtil.failure(ResultCode.INTERNAL_SERVER_ERROR);
        }
        //System.out.println(news.getUser_num());
        return ResultUtil.success(news);
    }

    @RequestMapping("updateVersion")
    public void updateVersion(@RequestBody Map<String, String> params){

    }
}
