package com.daou.moyeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	
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
	public String createGroup(HttpServletRequest request, @RequestParam Map<String, Object> reqParams, Authentication auth, Model model) {
		FileUtil fileUtil = new FileUtil();
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request; 
		List<Map<String, Object>> fileInfoList = fileUtil.fileUpload(mhsr);
		
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		Map<String, Object> groupMap = new HashMap<String, Object>();
		Map<String, Object> memGroupMap = new HashMap<String, Object>();
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		groupMap.put("groupName", reqParams.get("groupName"));
		groupMap.put("groupDesc", reqParams.get("groupDesc"));
		groupMap.put("groupImg", fileInfoList.get(0).get("FILE_STORED_NAME"));

		memGroupMap.put("groupName", reqParams.get("groupName"));
		memGroupMap.put("memberNO", u.getMemberNo());

		try {
			groupService.insertGroup(groupMap);
			groupService.insertMemberGroup(memGroupMap);
			transactionManager.commit(status);
			logger.info("transaction manager commit");
			
			int groupNo = groupService.selectGroupNo((String) reqParams.get("groupName"));

			List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
			gas.addAll(u.getAuthorities());
			gas.add(new SimpleGrantedAuthority("ROLE_GROUP" + groupNo + "_MASTER"));
			
			Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), gas);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.info("transaction manager rollback :: ", e);
		}
		
		return "redirect:/main";
	}
}