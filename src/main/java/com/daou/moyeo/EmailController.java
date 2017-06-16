package com.daou.moyeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	    
	    
	    @Autowired
	    private RedisTemplate<String, String> redisTemplate;

	    @Resource(name="redisTemplate")
	    private HashOperations<String, String, String> hashOps;
		
	    @RequestMapping(value = "/group/{groupNo}/email", method=RequestMethod.POST)
	    public String sendEmailAction (@PathVariable("groupNo") int groupNo, Authentication auth, HttpServletRequest req) throws Exception {
	    	HttpSession session = req.getSession();
	    	String receiverEmail = req.getParameter("receiverId");										 	
	    	String token;
	    	
	    	System.out.println("EmailController() - receiver : " + req.getParameter("receiverId"));
	        
	    	/* 수신 email 이 회원 인지 아닌지 확인하는 서비스
    	 	회원 -> MemberNo얻고 같은 그룹에서 온 초대는 제외검사 -> Token 생성 -> 그룹가입 msg 전송 -> 수신자 확인 url 접속 -> DB에 저장된 token 값 확인 -> 완료
    	 	url:/invite/groupNo/memberNo/token
    	 	비회원 -> token 생성 및 회원가입 url 전송 -> UserController에서 그룹가입 자동 완료
	    	url:/join/groupNo/notMember/token
	    	 */
	    	
	    	if(emailService.checkMemberOrNot(receiverEmail)){
	    		// 회원
	    		Map<String, Object> map = new HashMap<String, Object>();
	    		
		    	int receiverNo = emailService.getMemberNo(receiverEmail);
		    	map.put("groupNo", groupNo);
		    	map.put("memberNo", receiverNo);
		    	
		    	if(!emailService.checkTheSameInvitedGroupOrNot(map)){						//  같은 그룹에서 온 초대가 false라면 (초대가능)
		    		token = emailService.createToken(receiverNo, groupNo, receiverEmail);
			    	
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
	    		token = emailService.createToken(groupNo, receiverEmail);
	    		
	    		if(emailUtil.configureAndSend(session, receiverEmail, token, groupNo, -1)){		// 비회원 receiverNo : -1
	    			System.out.println("발송) 비회원 -> 성공");
		    		
	    		}else{
	    			System.out.println("발송) 비회원 -> 실패");
		    		
	    		}
	    		return "redirect:/group/" + groupNo;
	    	}
	    }
	    
	    /* 인증 URL 타고 온 요청 확인하는 메소드*/
	    @RequestMapping(value = "/invite/{groupNo}/{memberNo}/", method=RequestMethod.GET)
	    public String checkRequest(@PathVariable("groupNo") int groupNo, @PathVariable("memberNo") int memberNo, @RequestParam("joincode") String code, Authentication auth, Model model){
	    	
	    	/* Session 확인 
	    	 * groupNo, memberNo, token 으로 유효한 url 접근인지 확인. -> DB insert
	    	 * */
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	System.out.println("checkRequest() - code:"+code);
	    	String result = null;
	    	
	    	if(memberNo != -1) {
	    		if (auth != null) {
	    			UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
	    			if(memberNo != u.getMemberNo()){
	    				System.out.println("session 일치 X , " + memberNo + "/"+u.getMemberNo());
	    				return "/user/denied";
	    			}
	    		}
	    			
	    		result = hashOps.get(code, "token");
	    		
	    		if(code != result){
	    			// true (유효한 접근임을 확인)
	    			System.out.println("CODE_TB에 저장된 값과 url 값이 동일");
	    			// DB에 해당 회원 MEMBER_GROUP_TB에 새롭게 insert 해주는 Service 추가
	    			map.put("groupNo", groupNo);
		    		map.put("memberNo", memberNo);
		    		map.put("token", code);
		    		
	    			emailService.putNewMemberInGroup(map);
	    		}else{
	    			return "user/login";
	    		}
	    		
	    		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
	    		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
	    		gas.addAll(u.getAuthorities());
	    		gas.add(new SimpleGrantedAuthority("ROLE_GROUP" + groupNo + "_MEMBER"));
	    		
	    		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), gas);
	    		SecurityContextHolder.getContext().setAuthentication(newAuth);
	    		
	    		return "redirect:/main/"; 
	    	} else {
	    		Map<String, Object> notMemberInfo= new HashMap<String, Object>();
	    		
	    		notMemberInfo.put("code", code);
	    		notMemberInfo.put("groupNo", groupNo);
	    		notMemberInfo.put("email", hashOps.get(code, "email"));

	    		model.addAttribute("notMemberInfo", notMemberInfo);

	    		return "user/signUp"; 
	    	}
	    }
}
