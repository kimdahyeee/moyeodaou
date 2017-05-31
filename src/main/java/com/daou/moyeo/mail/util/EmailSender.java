package com.daou.moyeo.mail.util;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	@Autowired
    protected JavaMailSender mailSender;
 
    public boolean sendEmail(Email email) throws Exception {
         
        MimeMessage msg = mailSender.createMimeMessage();
        msg.setSubject(email.getSubject());
        msg.setText(email.getContent());
        msg.setRecipient(RecipientType.TO , new InternetAddress(email.getReciver()));
        
        /*
        try{
            mailSender.send(msg); 
            return true;
        }catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
        */
        mailSender.send(msg); 
        return true;
    }

}
