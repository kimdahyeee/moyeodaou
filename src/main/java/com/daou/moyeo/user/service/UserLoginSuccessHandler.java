package com.daou.moyeo.user.service;

import java.io.IOException;

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

public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
			logger.info(auth.getName());
			logger.info(auth.getAuthorities().toString());
		  logger.info(auth.getDetails().toString());
		  logger.info(auth.getPrincipal().toString());
		  for(GrantedAuthority a : auth.getAuthorities()){
		   logger.info(a.getAuthority());
		  }
		   
		  UserDetails u = (UserDetails) auth.getPrincipal();
		   
		  logger.info(String.valueOf(u.isAccountNonExpired()));
		  logger.info(String.valueOf(u.isAccountNonLocked()));
		  logger.info(String.valueOf(u.isCredentialsNonExpired()));
		  logger.info(String.valueOf(u.isEnabled()));
		   
		  System.out.println(u);
		  
		  if(getReturnUrl(req, res).equals("/daou")){
			  res.sendRedirect(req.getContextPath()+"/main");
		  } else {
			  res.sendRedirect(getReturnUrl(req, res));
		  }
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
