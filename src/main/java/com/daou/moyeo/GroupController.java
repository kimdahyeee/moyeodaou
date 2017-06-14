package com.daou.moyeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.daou.moyeo.group.service.GroupService;
import com.daou.moyeo.user.vo.UserDetailsVO;
import com.daou.moyeo.util.FileUtil;

@Controller
public class GroupController {

	@Resource(name="groupService")
	private GroupService groupService;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	/**
	 * 로그인 시 처음(메인) 화면
	 * @param model
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main(Model model, Authentication auth) {
		//TODO
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		List<Map<String, Object>> groupList = groupService.selectGroupList(u.getMemberNo());
		model.addAttribute("groupList", groupList);
		
		if(groupList.size() != 0) {
			model.addAttribute("groupNo", groupList.get(0).get("groupNo"));
		}
		
		return "main";
	}
	
	/**
	 * 새 그룹 생성
	 * @param request
	 * @param reqParams
	 * @param auth
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping( value = "/createGroup", method=RequestMethod.POST)
	public String fileUpload(HttpServletRequest request, @RequestParam Map<String, Object> reqParams, Authentication auth, Model model) throws Exception{
		FileUtil fileUtil = new FileUtil();
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request; 
		List<Map<String, Object>> fileInfoList = fileUtil.fileUpload(mhsr);
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		TransactionStatus status = transactionManager.getTransaction(def);
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		
		try {
			Map<String, Object> groupMap = new HashMap<String, Object>();
			groupMap.put("groupName", reqParams.get("groupName"));
			groupMap.put("groupDesc", reqParams.get("groupDesc"));
			groupMap.put("groupImg", fileInfoList.get(0).get("FILE_STORED_NAME"));
			groupService.insertGroup(groupMap);
			
			Map<String, Object> memGroupMap = new HashMap<String, Object>();
			memGroupMap.put("groupName", reqParams.get("groupName"));
			memGroupMap.put("memberNO", u.getMemberNo());
			groupService.insertMemberGroup(memGroupMap);
			
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			System.out.println("rollback");
		}
		
		int groupNo = groupService.selectGroupNo((String) reqParams.get("groupName"));
		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		gas.addAll(u.getAuthorities());
		gas.add(new SimpleGrantedAuthority("ROLE_GROUP" + groupNo + "_MASTER"));
		
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), gas);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		
		return "redirect:/main";
	}
}
