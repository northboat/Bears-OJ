package com.oj.neuqoj.api;

import com.oj.neuqoj.mapper.ReplyMapper;
import com.oj.neuqoj.mapper.TopicMapper;
import com.oj.neuqoj.pojo.Reply;
import com.oj.neuqoj.pojo.Result;
import com.oj.neuqoj.pojo.Topic;
import com.oj.neuqoj.utils.ResultCode;
import com.oj.neuqoj.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class TopicApi {

    private TopicMapper topicMapper;
    @Autowired
    public void setTopicMapper(TopicMapper topicMapper){
        this.topicMapper = topicMapper;
    }

    private ReplyMapper replyMapper;
    @Autowired
    public void setReplyMapper(ReplyMapper replyMapper){
        this.replyMapper = replyMapper;
    }


    @RequestMapping("/getTopics")
    public ResultUtil getTopic(@RequestBody Map<String, String> params){
        String tag = params.get("tag");
        if(tag.equals("All")){
            return ResultUtil.success(topicMapper.getAllTopic());
        }
        return ResultUtil.success(topicMapper.getTopicByTag(tag));
    }


    @RequestMapping("/getTopic")
    public ResultUtil getTopic(@RequestBody int num){
        Topic topic = topicMapper.getTopic(num);
        if(topic == null){
            return ResultUtil.failure(ResultCode.DATA_NOT_FOUND);
        }
        return ResultUtil.success(topic);
    }

    @RequestMapping("/getReply")
    public ResultUtil getReply(@RequestBody int topic){
        List<Reply> replies = replyMapper.getReply(topic);
        for(Reply reply: replies){
            reply.setReplies(replies);
        }
        List<Reply> res = new ArrayList<>();
        for(Reply reply: replies){
            if(reply.getTo() == 0){
                res.add(reply);
            }
        }
        return ResultUtil.success(res);
    }

    @RequestMapping("/reply")
    public ResultUtil reply(@RequestBody Map<String, Object> params){
        int topic = (int)params.get("topic");
        //System.out.println(params.get("to"));
        int to = (int)params.get("to");
        String from = params.get("from").toString();
        String content = params.get("content").toString();
        if(content.length() > 200){
            return ResultUtil.failure(ResultCode.PARAM_IS_INVALID);
        }
        replyMapper.reply(new Reply(topic, to, from, content));
        return ResultUtil.success();
    }


    @RequestMapping("/write")
    public ResultUtil write(@RequestBody Map<String, Object> params){
        try{
            String title = params.get("title").toString();
            String desc = params.get("desc").toString();
            String from = params.get("from").toString();
            String contact = params.get("contact").toString();
            String tag = params.get("tag").toString();
            String content = params.get("content").toString();

            Topic topic = new Topic(title, desc, from, contact, tag, content);

            topicMapper.addTopic(topic);
        }catch (Exception e){
            return ResultUtil.failure(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultUtil.success();
    }

    @RequestMapping("/getTopicsByName")
    public ResultUtil getTopicsByName(@RequestBody Map<String, String> params){
        String name = params.get("name");
        List<Topic> topics = topicMapper.getTopicsByName(name);
        return ResultUtil.success(topics);
    }

    @RequestMapping("/deleteTopic")
    public ResultUtil deleteTopic(@RequestBody Map<String, String> params){
        int num = Integer.parseInt(params.get("num"));
        topicMapper.deleteTopic(num);
        return ResultUtil.success();
    }
}
