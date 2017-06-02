package com.daou.moyeo.util;

import java.util.Random;

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
	 private EmailUtil emailSenderUtil;
	    
	public boolean sendMailAuth(HttpSession session, String receiver_id) {
    	EmailDTO email = new EmailDTO();
    	
        int ran = new Random().nextInt(100000) + 10000; 		// 10000 ~ 99999
        String joinCode = String.valueOf(ran);
        session.setAttribute("joinCode", joinCode);
        
        StringBuilder sb = new StringBuilder();
        sb.append("귀하의 인증 코드는 " + joinCode + " 입니다.");
        
        String reciver = receiver_id; 							//	받을사람의 이메일입니다.
        String subject = "그룹초대 인증 코드 발급 안내 입니다.";			//  이메일 제목
	    String content = sb.toString();						// 	이메일 내용
        
	    // TODO url 설정하기
	    content = content + "\n" +"http://localhost:8181/daou/invite?joincode=" + joinCode;				
	    
	    email.setReciver(reciver);
        email.setSubject(subject);
        email.setContent(content);
        
        try {													// 이메일 발송 성공 , 실패 ??
			return emailSenderUtil.sendEmail(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("hee hee hee");
			e.printStackTrace();
			return false;
		}						
        
    }
	
    public boolean sendEmail(EmailDTO emailDTO) throws Exception {
         
        MimeMessage msg = mailSender.createMimeMessage();
        msg.setSubject(emailDTO.getSubject());
        msg.setText(emailDTO.getContent());
        msg.setRecipient(RecipientType.TO , new InternetAddress(emailDTO.getReciver()));
        
        mailSender.send(msg); 
        return true;
    }
    
    

}
