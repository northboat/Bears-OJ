package com.oj.neuqoj.utils;


import lombok.SneakyThrows;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil extends Thread {

    //邮件信息
    private static String from;
    private MimeMessage message;
    private static Session session;

    static{
        from = "northboat@qq.com";

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

    //生成6位验证码，包含数字、小写字母、大写字母
    public String createCode(){
        char[] code = new char[6];
        for(int i = 0; i < 6; i++){
            //floor向下取整，random生成数[0,1)
            int flag = (int)Math.floor(1+Math.random()*3);
            switch(flag){
                case 1:
                    //48-57数字
                    code[i] = (char)Math.floor(48+Math.random()*10);
                    break;
                case 2:
                    code[i] = (char)Math.floor(97+Math.random()*26);
                    break;
                case 3:
                    code[i] = (char)Math.floor(65+Math.random()*26);
                    break;
            }
        }
        return new String(code);
    }

    public MailUtil(String to) throws MessagingException {
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Hello NEUQer!");
    }

    public String ready() throws MessagingException {
        String code = createCode();
        //System.out.println(code);
        message.setText("欢迎使用我的OJ！这是你的验证码：" + code);
        return code;
    }

    @SneakyThrows
    @Override
    public void run() {
        try{
            Transport.send(message);
        } catch (MessagingException e){
            throw e;
        }
    }

//    public static void main(String[] args) throws MessagingException {
//        Postman p = new Postman("1543625674@qq.com");
//        p.start();
//    }

}
