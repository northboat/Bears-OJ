package com.postoffice.service;

import com.postoffice.mapper.MailMapper;
import com.postoffice.vo.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import java.util.*;

//vnufsybifrabicjj SMTP/POP3(154)
//oxftgstrzznrbddc SMTP/POP3(193)
//bgmrnmqksqabbfaa IMAP/SMTP


public class PostOffice {

    private static int num;
    private static Session session;
    private static Map<Integer, Postman> office;


    static{
        office = new HashMap<>();

        Properties properties = new Properties();
        properties = System.getProperties();
        //设置第三方服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        //开启密码验证
        properties.setProperty("mail.smtp.auth", "true");
        //设置超时时间
        properties.setProperty("mail.smtp.timeout", "4000");
        //开启debug
        properties.setProperty("mail.debug", "true");

        //开启ssl服务
        properties.setProperty("mail.smtp.ssl.enable", "true");
        //设置端口
        properties.setProperty("mail.smtp.port", "465");
        //设置ssl端口，必要的，否则连接不上
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("northboat@qq.com", "oxftgstrzznrbddc");
            }
        });
    }

    public static void flush(List<Mail> mails){
        for(Mail mail: mails){
            if(!PostOffice.has(mail.getNum())){
                //System.out.println("进来了");
                Postman postman = new Postman();
                try {
                    postman.ready(session, mail);
                } catch (MessagingException e) {
                    System.out.println("初始化邮件" + mail.getNum() + "失败，已跳过");
                    continue;
                }
                //System.out.println(mail.getNum() + " " + mail.getName());
                office.put(mail.getNum(), postman);
            }
        }

    }

    public static void beginWork(){
        for(Postman postman: office.values()){
            postman.start();
        }
        num = office.size()-1;
    }

    public static Mail send(Mail mail) {
        //在此处设置编号
        mail.setNum(++num);
        try {
            //在ready函数中设置from
            Postman postman = new Postman(session, mail);
            postman.start();
            if(postman.isStopped()){
                return null;
            }
            office.put(num, postman);
        } catch (MessagingException e) {
            System.out.println("初始化邮差报错，准备邮件失败");
        }
        return mail;
    }

    public static boolean has(int num){
        return office.getOrDefault(num, null) != null;
    }

    public static void remove(int num){
        office.get(num).shutdown();
        //office.get(num).destroy();
        office.remove(num);
    }

    public static Collection<Postman> getPostmen(){
        return office.values();
    }

    public static Postman getPostman(int num){
        return office.get(num);
    }
}
