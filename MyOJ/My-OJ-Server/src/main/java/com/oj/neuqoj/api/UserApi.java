package com.oj.neuqoj.api;

import com.oj.neuqoj.mapper.InfoMapper;
import com.oj.neuqoj.mapper.NewsMapper;
import com.oj.neuqoj.mapper.QuestionMapper;
import com.oj.neuqoj.mapper.UserMapper;
import com.oj.neuqoj.pojo.Info;
import com.oj.neuqoj.pojo.Result;
import com.oj.neuqoj.pojo.User;
import com.oj.neuqoj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.*;

@RestController
@CrossOrigin
public class UserApi {

    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    private NewsMapper newsMapper;
    @Autowired
    public void setNewsMapper(NewsMapper newsMapper){
        this.newsMapper = newsMapper;
    }

    private InfoMapper infoMapper;
    @Autowired
    public void setDetailMapper(InfoMapper infoMapper){
        this.infoMapper = infoMapper;
    }

    private QuestionMapper questionMapper;
    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper){
        this.questionMapper = questionMapper;
    }

    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }


    @RequestMapping("/login")
    public @ResponseBody
    ResultUtil login(@RequestBody Map<String, String> params){
        String username = params.get("username");
        String pwd = params.get("password");
        //System.out.println(username + " " + pwd);
        User u1 = userMapper.getUserByName(username);
        User u2 = userMapper.getUserByAccount(username);
        User user = u1==null ? u2:u1;
        if(user == null){
            System.out.println("该用户不存在");
            return ResultUtil.failure(ResultCode.USER_NOT_EXIST);
        } else if(!user.getPassword().equals(pwd)){
            System.out.println("密码错误");
            return ResultUtil.failure((ResultCode.USER_PASSWORD_ERROR));
        }
        System.out.println("成功登录");
        newsMapper.visitUp(NewsApi.version);
        return ResultUtil.success(user);
    }


    @RequestMapping("/send")
    public @ResponseBody
    ResultUtil send(@RequestBody Map<String, String> params){
        String to = params.get("email");
        if(userMapper.getUserByAccount(to) != null){
            return ResultUtil.failure(ResultCode.USER_HAS_EXISTED, "忘记密码？请在登录页面点击 forget password 找回密码");
        }
        try {
            MailUtil mailUtil = new MailUtil(to);
            String code = mailUtil.ready();
            mailUtil.start();
            //registerCode.put(to, code);
            redisUtil.set(to, code, 600);
            System.out.println(to + ":" + code);
        } catch (MessagingException e) {
            System.out.println("发送邮件出错");
            return ResultUtil.failure(ResultCode.INTERNAL_SERVER_ERROR, "发送邮件出错");
        }
        return ResultUtil.success();
    }

    @RequestMapping("/getCode")
    public ResultUtil getCode(@RequestBody Map<String, String> params){
        String to = params.get("email");
        User user = userMapper.getUserByAccount(to);
        if(user == null){
            return ResultUtil.failure(ResultCode.PARAM_IS_BLANK, "账号不存在，请先注册");
        }
        try {
            MailUtil mailUtil = new MailUtil(to);
            String code = mailUtil.ready();
            mailUtil.start();

            redisUtil.set(to, code, 600);//单位秒
            //registerCode.put(to, code);
            System.out.println(to + ":" + code);
        } catch (MessagingException e) {
            System.out.println("发送邮件出错");
            return ResultUtil.failure(ResultCode.INTERNAL_SERVER_ERROR, "发送邮件出错");
        }

        //System.out.println(redisUtil.get(to));
        return ResultUtil.success();
    }


    @RequestMapping("/change")
    public ResultUtil change(@RequestBody Map<String, String> params){
        String account = params.get("email");
        String code = params.get("codes");
        if(!redisUtil.get(account).equals(code)){
            return ResultUtil.failure(ResultCode.CODE_VERIFY_FAILURE);
        }

        String password = params.get("password");
        User user = new User(account, password);
        userMapper.changePassword(user);
        redisUtil.del(account);
        return ResultUtil.success();
    }


    @RequestMapping("/verify")
    public @ResponseBody
    ResultUtil verify(@RequestBody Map<String, String> params){
        String email = params.get("email");
        String codes = params.get("codes");
        System.out.println(email + ":" + codes);
        if(redisUtil.get("email").equals(codes)){
            return ResultUtil.success("验证成功");
        }
        return ResultUtil.failure(ResultCode.CODE_VERIFY_FAILURE);
    }


    @RequestMapping("/userList")
    public ResultUtil list(){
        List<String> list = userMapper.getAllUsersName();
        return ResultUtil.success(list);
    }


    @RequestMapping("/register")
    public ResultUtil register(@RequestBody Map<String, String> params){
        String account = params.get("account");
        String username = params.get("username");
        String password = params.get("password");
        User newUser = new User(account, username, password, 0, 1);
        userMapper.addUser(newUser);
        redisUtil.del(account);

        newsMapper.userUp(NewsApi.version);
//        String account = "das";
//        String username = "das";
        infoMapper.addInfo(new Info(account, username, DateUtil.parseDate(new Date()), questionMapper.getAllQuestions().size()));
        return ResultUtil.success("注册成功");
    }


    @RequestMapping("/getTopUser")
    public ResultUtil getTopUser(){
        List<Info> list = infoMapper.getAllInfo();

        if(list == null){ return ResultUtil.failure(ResultCode.INTERNAL_SERVER_ERROR); }

        list.sort((i1, i2) -> { return i2.getFinished() - i1.getFinished(); });

        if(list.size() > 7){
            list = list.subList(0, 7);
        }
        return ResultUtil.success(list);
    }

    @RequestMapping("/getInfoByName")
    public ResultUtil getInfoByName(@RequestBody Map<String, String> params){
        String name = params.get("name");
        Info info = infoMapper.getInfoByName(name);
        return ResultUtil.success(info);
    }
}
