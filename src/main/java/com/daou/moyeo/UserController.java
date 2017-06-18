package com.daou.moyeo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daou.moyeo.mail.service.EmailService;
import com.daou.moyeo.user.dao.UserService;
import com.daou.moyeo.user.util.PasswordEncoding;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="bcryptEncoder")
	private PasswordEncoding encoder;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="emailService")
	private EmailService emailService;
		
	/**
	 * 로그인 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/")
	public String login(Model model) {
		return "user/login";
	}
	
	/**
	 * 회원가입 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signUp")
	public String signUp(Model model) {
		return "user/signUp";
	}
	
	/**
	 * 회원가입
	 * @param req
	 * @param res
	 * @param email
	 * @param name
	 * @param password
	 * @throws IOException
	 * @author KimDaHye 20170615 수정(이메일관련)
	 */
	@ResponseBody
	@RequestMapping( value="/user/insertUser", method=RequestMethod.POST )
	public HashMap<String, String> insertUser( HttpServletRequest req, HttpServletResponse res, @RequestParam("email") String email,
										 @RequestParam("name") String name,
										 @RequestParam("password") String password, 
										 @RequestParam(value="code", required=false) String code,
										 @RequestParam(value="groupNo", required=false) Integer groupNo) throws IOException {
		
		int userResult = 0;
		int emailResult = 0;
		String encodePW = encoder.encode(password);
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		userMap.put("email", email);
		userMap.put("name", name);
		userMap.put("authority", "ROLE_USER");
		userMap.put("password", encodePW);
		
		try{
			userResult = userService.insertUser(userMap);
		} catch (Exception e) {
			System.out.println(e);
			resultMap.put("KEY", "FAIL");
		}
		
		if(code != null) {
			Map<String, Object> groupMap = new HashMap<String, Object>();
			int memberNo = emailService.getMemberNo(email);

			groupMap.put("groupNo", groupNo);
			groupMap.put("memberNo", memberNo);
			groupMap.put("token", code);
			emailResult = emailService.putNewMemberInGroup(groupMap);
		}
		
		if (emailResult == 1 ) {
			resultMap.put("KEY", "EMAIL_SUCCESS");
		} else if ( userResult == 1 ) {
			resultMap.put("KEY", "USER_SUCCESS");
		}

		return resultMap;
	}
	
	@RequestMapping(value = "/denied")
	public String deniedView() {
		return "user/denied";
	}
}
 