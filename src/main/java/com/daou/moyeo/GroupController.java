package com.daou.moyeo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.daou.moyeo.board.dao.BoardService;
import com.daou.moyeo.group.service.GroupService;
import com.daou.moyeo.user.vo.UserDetailsVO;
import com.daou.moyeo.util.FileUtil;

@Controller
public class GroupController {

	@Resource(name="groupService")
	private GroupService groupService;
	
	@RequestMapping(value = "/main")
	public String main(Model model, Authentication auth) {
		//TODO
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		List<Map<String, Object>> groupList = groupService.selectGroupList(u.getMemberNo());
		model.addAttribute("groupList", groupList);
		return "main";
	}
	
	@RequestMapping( value = "/createGroup", method=RequestMethod.POST)
	public String fileUpload(HttpServletRequest request, @RequestParam Map<String, Object> reqParams){
		FileUtil fileUtil = new FileUtil();
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request; 
		List<Map<String, Object>> fileInfoList = fileUtil.fileUpload(mhsr);
		
		
		System.out.println(fileInfoList.get(0).get("FILE_STORED_NAME"));
		
		return "redirect:/main";
	}
}
