package com.postoffice.service;

import com.postoffice.vo.Mail;

import javax.mail.MessagingException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;



public class Postman extends Thread{

    //邮件信息
    private String from;
    private Mail mail;
    private MimeMessage message;
    //使线程停止
    private boolean stop;

    public Mail getMail(){
        return mail;
    }

    public Postman(){
        stop = false;
        from = "northboat@qq.com";
    }

    public Postman(Session session, Mail mail) throws MessagingException {
        stop = false;
        from = "northboat@qq.com";
        mail.setCount(0);
        mail.setFrom(from);
        this.mail = mail;
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail.getTo()));
        message.setSubject("Hello");
        message.setText("这是由" + mail.getName() + "为您订阅的邮件，将会每周定时为你发送");
    }


    public boolean isStopped(){
        return stop;
    }


    public void ready(Session session, Mail mail) throws MessagingException {
        this.mail = mail;
        this.mail.setFrom(from);
        //this.mail.setCount(0);
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail.getTo()));
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
    }

    @Override
    public void run() {
        do{
            try {
                //say hello
                if(mail.getCount() == 0){
                    message.setSubject("Hello");
                    message.setText("这是由" + mail.getName() + "为您订阅的邮件，将会每周定时为你发送");
                    Transport.send(message);
                    System.out.println("提示邮件发送成功");
                    TimeUnit.SECONDS.sleep(20);

                    message.setSubject(mail.getSubject());
                    message.setText(mail.getText());
                }

                Transport.send(message);

                System.out.println(mail.getName() + "给" + mail.getTo() + "的第" + (mail.getCount()+1) + "封邮件发送成功");
                mail.setCount(mail.getCount()+1);

                TimeUnit.DAYS.sleep(7);
            } catch (Exception e){
                System.out.println("线程异常，已中断");
                e.printStackTrace();
                stop = true;
                if(PostOffice.has(mail.getNum())){
                    PostOffice.remove(mail.getNum());
                }
                break;
            }
        } while(!stop);

        //say goodbye
        try {
            message.setSubject("goodbye");
            message.setText("订阅已结束，爷光荣下班");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("bye, i am gone");
    }

    public void shutdown(){
        stop = true;
    }
}
