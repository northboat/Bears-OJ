package com.postoffice.controller;


import com.postoffice.mapper.MailMapper;
import com.postoffice.service.PostOffice;
import com.postoffice.vo.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MailController {

    private boolean firstStart = true;

    private MailMapper mailMapper;
    @Autowired
    public void setMailMapper(MailMapper mailMapper){
        this.mailMapper = mailMapper;
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       Model model, HttpSession session){
        if(username.equals("") || password.equals("")){
            model.addAttribute("msg", "用户名或密码不能为空");
            return "index";
        }
        if(!password.equals("123456")){
            model.addAttribute("msg", "密码错误");
        }
        session.setAttribute("loginUser", username);
        List<Mail> mails = mailMapper.queryMailList();
        PostOffice.flush(mails);
        //每次登录更新一遍每封邮件发送的次数
        for(Mail mail: mails){
            int count = PostOffice.getPostman(mail.getNum()).getMail().getCount();
            if(mail.getCount() != count){
                System.out.println("开始修改当前邮件发送次数");
                Map<String, Integer> map = new HashMap<>();
                map.put("num", mail.getNum());
                map.put("count", count);
                mailMapper.updateMailCount(map);
            }
        }
        //首次启动，开始发送所有邮件，启动所有线程
        if(firstStart){
            PostOffice.beginWork();
            firstStart = false;
        }
        model.addAttribute("postmen", PostOffice.getPostmen());
//        for(Postman p: PostOffice.getPostmen()){
//            System.out.println(p.getMail().getNum() + p.getMail().getName());
//        }
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "index";
    }

    @RequestMapping("/send")
    public String send(@RequestParam("name")String name,
                       @RequestParam("to")String to,
                       @RequestParam("subject")String subject,
                       @RequestParam("text")String text){
        Mail mail = new Mail(name, to, subject, text);
        Mail m = PostOffice.send(mail);
        //System.out.println(m.getNum() + m.getFrom());
        mailMapper.addMail(m);
        return "redirect:/main";
    }

    @RequestMapping("/main")
    public String main(Model model){
        PostOffice.flush(mailMapper.queryMailList());
        model.addAttribute("postmen", PostOffice.getPostmen());
        return "main";
    }

    @RequestMapping("/drop/{num}")
    public String drop(@PathVariable("num")Integer num){
        PostOffice.remove(num);
        mailMapper.removeMail(num);
        return "redirect:/main";
    }
}
