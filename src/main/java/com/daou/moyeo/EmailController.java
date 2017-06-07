package com.daou.moyeo;

import java.util.HashMap;
import java.util.Map;

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
    	 	회원 -> MemberNo얻고 같은 그룹에서 온 초대는 제외검사 -> Token 생성 -> 그룹가입 msg 전송 -> 수신자 확인 url 접속 -> DB에 저장된 token 값 확인 -> 완료
    	 	비회원 -> 회원가입 msg 전송 -> ...(회원가입 url ???) + token -> 그룹가입 자동 완료
	    	 */
	    	
	    	
	    	if(emailService.checkMemberOrNot(receiverEmail)){
	    		// 회원
	    		Map<String, Object> map = new HashMap<String, Object>();
	    		
		    	int receiverNo = emailService.getMemberNo(receiverEmail);
		    	map.put("groupNo", groupNo);
		    	map.put("memberNo", receiverNo);
		    	
		    	if(!emailService.checkTheSameInvitedGroupOrNot(map)){						//  같은 그룹에서 온 초대가 false라면 (초대가능)
		    		token = emailService.createToken(receiverNo, groupNo);
			    	
		    		if(emailUtil.configureAndSend(session, receiverEmail, token, groupNo, receiverNo)){
			        	// 성공
		    			System.out.println("발송) 회원 -> 성공");
		    			return "redirect:/group/" + groupNo;
			        }else{
			        	// 실패
			        	System.out.println("발송) 회원 -> 실패");
			        	return "redirect:/group/" + groupNo;
			    	}
		    	}else{																		// 같은 그룹에서 온 초대
		    		System.out.println("같은 그룹에서 온 초대 발송 X");
		    		return "redirect:/group/" + groupNo;
		    	}
		    	
	    	}else{
	    		System.out.println("발송) 비회원 ");
	    		return "redirect:/group/" + groupNo;
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
	    @RequestMapping(value = "/invite/{groupNo}/{memberNo}/", method=RequestMethod.GET)
	    public String checkRequest(@PathVariable("groupNo") int groupNo, @PathVariable("memberNo") int memberNo, @RequestParam("joincode") String code){
	    	
	    	/* Session 확인을 통해 로그인 / 비로그인 구분
	    	 * groupNo, memberNo, token 으로 유효한 url 접근인지 확인. -> DB insert
	    	 * */
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	System.out.println("checkRequest() - code:"+code);
	    	
	    	map.put("groupNo", groupNo);
	    	map.put("memberNo", memberNo);
	    	map.put("token", code);
	    	//UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
	    	//u.getMemberNo();
	    	
	    	if(emailService.checkToken(map)){
	    		// true (유효한 접근임을 확인)
	    		System.out.println("CODE_TB에 저장된 값과 url 값이 동일");
	    		// DB에 해당 회원 MEMBER_GROUP_TB에 새롭게 insert 해주는 Service 추가
	    		emailService.putNewMemberInGroup(map);
	    	}else{
	    		System.out.println("groupNo, memberNo, token 중 DB에 존재하는 값과 일치하지 않음. ");  	
	    	}
	    	
	    	// session 확인 ?? 
	    	return "redirect:/main/"; 
	    }
}
