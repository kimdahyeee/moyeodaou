package com.daou.moyeo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daou.moyeo.mail.service.EmailService;
import com.daou.moyeo.user.vo.UserDetailsVO;
import com.daou.moyeo.util.EmailUtil;

@Controller
public class EmailController {
	
	    @Autowired
	    private EmailUtil emailUtil;
	    
	    @Resource(name="emailService")
		private EmailService emailService;
		
	    /*
	     * 		그룹 초대 modal창 클릭시 
	     * */
	    @RequestMapping(value = "/group/{groupNo}/email", method=RequestMethod.POST)
	    public String sendEmailAction (@PathVariable("groupNo") int groupNo, Authentication auth, HttpServletRequest req) throws Exception {
	    	HttpSession session = req.getSession();
	    	String receiverEmail = req.getParameter("receiverId");										 	
	    	String token;
	    	
	    	System.out.println("EmailController() - receiver : " + req.getParameter("receiverId"));
	        
	    	/* 수신 email 이 회원 인지 아닌지 확인하는 서비스
    	 	회원 -> Token 생성 -> 그룹가입 msg 전송 -> 수신자 확인 url 접속 -> DB에 저장된 token 값 확인 -> 완료
    	 	비회원 -> 회원가입 msg 전송 -> ...(회원가입 url ???) + token -> 그룹가입 자동 완료
	    	 */
	    	
	    	
	    	if(emailService.checkMemberOrNot(receiverEmail)){
	    		// 회원
	    		
		    	int receiverNo = emailService.getMemberNo(receiverEmail);
		    	token = emailService.createToken(receiverNo, groupNo);
		    	
	    		if(emailUtil.configureAndSend(session, receiverEmail, token, groupNo)){
		        	// 성공
	    			System.out.println("발송) 회원 -> 성공");
	    			return "redirect:/group/" + groupNo;
		        }else{
		        	// 실패
		        	System.out.println("발송) 회원 -> 실패");
		            return "redirect:/main";
		    	}
	    	}else{
	    		System.out.println("발송) 비회원 ");
	    	/*
	    		// 비회원
	    		if(emailUtil.configureAndSend(session, receiverEmail, token)){
		        	// 성공
		            return "redirect:/groupMain";
		        }else{
		        	// 실패
		            return "redirect:/main";
		    	}
	    	*/
	    	}
	    }
	    
	    /* 인증 URL 타고 온 요청 확인하는 메소드*/
	    @RequestMapping(value = "/invite/{groupNo}/", method=RequestMethod.GET)
	    public String checkRequest(@PathVariable("groupNo") int groupNo, @RequestParam("joincode") String code){
	    	
	    	/* DB에 저장된 인증코드 확인 */
	    	System.out.println("checkRequest() - code:"+code);
	    	
	    	if(emailService.checkToken(code)){
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
