package com.daou.moyeo;

import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daou.moyeo.mail.service.CheckingCodeService;
import com.daou.moyeo.mail.util.Email;
import com.daou.moyeo.mail.util.EmailSender;

@Controller
public class EmailController {
	    @Autowired
	    private EmailSender emailSender;
	    
	    @Resource(name="checkingCodeService")
		private CheckingCodeService checkingCodeService;
		
	    private boolean sendMailAuth(HttpSession session, String receiver_id) {
	    	Email email = new Email();
	    	
	        int ran = new Random().nextInt(100000) + 10000; 		// 10000 ~ 99999
	        String joinCode = String.valueOf(ran);
	        session.setAttribute("joinCode", joinCode);
	        
	        StringBuilder sb = new StringBuilder();
	        sb.append("귀하의 인증 코드는 " + joinCode + " 입니다.");
	        
	        String reciver = receiver_id; 							//	받을사람의 이메일입니다.
	        String subject = "그룹초대 인증 코드 발급 안내 입니다.";			//  이메일 제목
		    String content = sb.toString();						// 	이메일 내용
	        
		    content = content + "\n" +"http://localhost:8080/daou/invite?joincode=" + joinCode;				// URL ?? 
		    
		    email.setReciver(reciver);
	        email.setSubject(subject);
	        email.setContent(content);
	        
	        try {													// 이메일 발송 성공 , 실패 ??
				return emailSender.sendEmail(email);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("hee hee hee");
				e.printStackTrace();
				return false;
			}						
	        
	    }
	 
	    @RequestMapping(value = "/email", method=RequestMethod.POST)
	    public String sendEmailAction (HttpServletRequest req) throws Exception {
	    	HttpSession session = req.getSession();
	    	
	    	String addr = req.getParameter("receiver_id");										 	// 수신 email
	        
	    	System.out.println("EmailController() - receiver : " + req.getParameter("receiver_id"));
	        
	    	/* 수신 email 이 회원 인지 아닌지 확인하는 서비스
	    	
	    	 	회원 -> 그룹가입 msg 전송 -> 수신자 확인 url 접속 -> DB에 저장된 token 값 확인 -> 완료
	    	 	비회원 -> 회원가입 msg 전송 -> ...(회원가입 url ???)  -> 그룹가입 자동 완료
	    	 
	    	*/
	    	
	        if(sendMailAuth(session, addr)){
	        	// 성공
	            return "redirect:/groupMain";
	        }else{
	        	// 실패
	            return "redirect:/main";
	    	}
	        
	        //return new ModelAndView("success");
	    }
	    
	    /* 인증 URL 타고 온 요청 확인하는 메소드*/
	    @RequestMapping(value = "/invite", method=RequestMethod.GET)
	    public String checkRequest(@RequestParam("joincode") String code){
	    	
	    	/* DB에 저장된 인증코드 확인 */
	    	System.out.println("checkRequest() - code:"+code);
	    	
	    	if(checkingCodeService.checkCode(code)){
	    		// true (인증코드 확인)
	    		System.out.println("그룹가입 완료창?");
	    		// DB에 해당 회원 GROUP_TB에 새롭게 insert 해주는 Service 추가
	    		// ....
	    	}else{
	    		System.out.println("그룹가입 X");  	
	    	}
	    	
	    	return "redirect:/groupMain"; 
	    }
}
