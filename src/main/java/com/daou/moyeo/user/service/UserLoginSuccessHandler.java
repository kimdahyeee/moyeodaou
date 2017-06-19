package com.daou.moyeo.user.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.daou.moyeo.user.vo.UserDetailsVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
		
		  UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		  System.out.println(u);
		  ObjectMapper om = new ObjectMapper();
		  Map<String, Object> map = new HashMap<String, Object>();
		  
		  map.put("KEY", "SUCCESS");
		  if(getReturnUrl(req, res).equals("/daou")){
			  map.put("RETURNURI", req.getContextPath()+"/main");
		  } else {
			  map.put("RETURNURI", getReturnUrl(req, res));
		  }
		  
		  String returnJson = om.writeValueAsString(map);
		  OutputStream out = res.getOutputStream();
		  out.write(returnJson.getBytes());
	}
	
	/** 
	 * 이전 페이지로 이동
	 * @param request 
	 * @param response 
	 * @return 
	 * */ 
	private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) { 
		
		RequestCache requestCache = new HttpSessionRequestCache(); 
		SavedRequest savedRequest = requestCache.getRequest(request, response); 
		if (savedRequest == null) { 
			return request.getSession().getServletContext().getContextPath(); 
		} 
		return savedRequest.getRedirectUrl(); 
	}

}
