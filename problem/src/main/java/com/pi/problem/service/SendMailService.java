package com.pi.problem.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {


    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailMessage(String target,String subject,String body){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(target);
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);
        //System.out.println("message sent ");
    }

}