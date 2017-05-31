package com.daou.moyeo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

import com.daou.moyeo.board.dao.BoardService;
import com.daou.moyeo.user.dao.UserService;
import com.daou.moyeo.user.util.PasswordEncoding;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource(name="bcryptEncoder")
	private PasswordEncoding encoder;
	
	@Resource(name="userService")
	private UserService userService;
		
	@Resource(name="boardService")
	private BoardService boardService;
	
	@RequestMapping(value = "/groupMain")
	public String groupMain(Model model) {
		List<Map<String, Object>> allMainBoardList = boardService.selectMainBoardList(1);
		model.addAttribute("allMainBoardList", allMainBoardList);
		return "groupMain";
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/")
	public String login(Model model) {
		return "user/login";
	}
	
	@RequestMapping(value = "/main")
	public String main(Model model) {
		return "main";
	}
	
	@RequestMapping(value = "/signUp")
	public String signUp(Model model) {
		return "user/signUp";
	}
	
	@RequestMapping( value="/user/insertUser", method=RequestMethod.POST )
	public void insertUser( HttpServletRequest req, HttpServletResponse res, @RequestParam("email") String email,
										 @RequestParam("name") String name,
										 @RequestParam("password") String password) throws IOException {
		String encodePW = encoder.encode(password);
		
		Map<String, String> userMap = new HashMap<String, String>();
		userMap.put("email", email);
		userMap.put("name", name);
		userMap.put("authority", "ROLE_USER");
		userMap.put("password", encodePW);
		
		int result = userService.insertUser(userMap);
		logger.info("result ======>" + result);
		res.sendRedirect(req.getContextPath()+"/");
	}
	
}
 