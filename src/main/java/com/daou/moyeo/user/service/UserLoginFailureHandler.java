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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserLoginFailureHandler implements AuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException auth)
			throws IOException, ServletException {

		logger.info(auth.getLocalizedMessage());
		logger.info(auth.getMessage());

		for(StackTraceElement s : auth.getStackTrace()){
			logger.info(s.getClassName());
			logger.info(s.getFileName());
			logger.info(s.getMethodName());
			logger.info(s.getLineNumber()+"");
			logger.info(s.isNativeMethod()+"");
		}	

		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("KEY", "FAIL");

		String returnJson = om.writeValueAsString(map);
		OutputStream out = res.getOutputStream();
		out.write(returnJson.getBytes());

		req.setAttribute("errMsg",auth.getMessage());
	}
}
