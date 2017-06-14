package com.daou.moyeo.util;

import java.util.Random;
import java.util.UUID;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.daou.moyeo.dto.EmailDTO;

@Component
public class EmailUtil {
	 @Autowired
     protected JavaMailSender mailSender;
 
	 @Autowired
	 private EmailUtil emailUtil;
	 
	 /*
	  	 configoureAndSend -> sendEmail 의 흐름으로 그룹초대 email 발송
	  */
	public boolean configureAndSend(HttpSession session, String receiverEmail, String joinCode, int groupNo, int memberNo) {
    	EmailDTO email = new EmailDTO();
    	
    	session.setAttribute("joinCode", joinCode);
        
        StringBuilder sb = new StringBuilder();
        sb.append("귀하의 인증 코드는 " + joinCode + " 입니다.");
        
        String reciver = receiverEmail; 							
        String subject = "그룹초대 인증 코드 발급 안내 입니다.";			
	    String content = sb.toString();						
	    
	    // TODO url 설정하기	
	    content = content + "\n" +"http://172.21.22.137:8181/daou/invite/" + groupNo + "/" + memberNo + "/?joincode=" + joinCode;				
	    
	    email.setReciver(reciver);
        email.setSubject(subject);
        email.setContent(content);
        
        try {													// 이메일 발송 성공 , 실패 ??
			return emailUtil.sendEmail(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("hee hee hee");
			e.printStackTrace();
			return false;
		}						
        
    }
	
	/*
	 * 설정된 Mime Msg로 발송
	 * */
    private boolean sendEmail(EmailDTO emailDTO) throws Exception {
         
        MimeMessage msg = mailSender.createMimeMessage();
        msg.setSubject(emailDTO.getSubject());
        msg.setText(emailDTO.getContent());
        msg.setRecipient(RecipientType.TO , new InternetAddress(emailDTO.getReciver()));
        
        mailSender.send(msg); 
        return true;
    }
    
    

}
