package com.daou.moyeo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.daou.moyeo.board.service.BoardService;
import com.daou.moyeo.group.service.GroupService;
import com.daou.moyeo.schedule.service.ScheduleService;
import com.daou.moyeo.user.service.FileService;
import com.daou.moyeo.user.vo.UserDetailsVO;
import com.daou.moyeo.util.CalculateSchedule;
import com.daou.moyeo.util.FileUtil;

@Controller
public class GroupMainController {
	private static final Logger logger = LoggerFactory.getLogger(GroupMainController.class);
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	@Resource(name="groupService")
	private GroupService groupService;
	
	@Resource(name="scheduleService")
	private ScheduleService scheduleService;    
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name="redisTemplate")
	private HashOperations<String, String, String> hashOps;
	
	/**
	 *  그룹 메인 화면
	 * @param model
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/group/{groupNo}")
	public String groupMainInit(@PathVariable("groupNo") int groupNo,
			Model model,
			Authentication auth) {		
		
		//Daeho 2017.06.16 schedule
		Map<String, String> availableDate = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>(
				Arrays.asList("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"));
		CalculateSchedule cs = new CalculateSchedule(scheduleService, redisTemplate, hashOps);
		
		
		List<String> values = hashOps.multiGet("available_date:"+groupNo, dayList);
		
		//Daeho 2017.06.07 chat
		Map<String, Object> currentInfo = new HashMap<String, Object>();
		UserDetailsVO u = (UserDetailsVO)auth.getPrincipal();
		currentInfo.put("memberNo", u.getMemberNo());
		currentInfo.put("memberName", u.getMemberName());
		currentInfo.put("groupNo", groupNo);
		currentInfo.put("weekStartDate", cs.getThisWeekStartDate());
		
		List<Map<String, Object>> sharingList = fileService.getFileList(groupNo);  // load Group Fille List
		List<Map<String, Object>> allMainBoardList = boardService.selectMainBoardList(groupNo); // load Group Board List
		
		Map<String, Object> groupInfo = groupService.selectGroupInfo(groupNo); // daeho 2017.06.07 chat
		List<Map<String, Object>> otherGroupList = groupService.selectOtherGroupList(currentInfo); // daeho 2017.06.07 chat
		List<Map<String, Object>> groupMemberList = groupService.selectGroupMemberList(currentInfo);
		List<Map<String, Object>> addedScheduleMemberList = scheduleService.selectAddedScheduleMember(currentInfo);
		
		int i = 0;

		if(values.get(0) == null) {
			System.out.println("Redis null!");
			cs.setGroupNo(groupNo);
			cs.calculateSchedule();
			values = hashOps.multiGet("available_date:"+groupNo, dayList);
		}
		
		for (String day: dayList) {
			availableDate.put(day, values.get(i++));
		}
		
		
		model.addAttribute("sharingList", sharingList);
		model.addAttribute("allMainBoardList", allMainBoardList);
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("groupInfo", groupInfo); // daeho 2017.06.07 chat
		model.addAttribute("otherGroupList", otherGroupList); 
		model.addAttribute("groupMemberList", groupMemberList); 
		model.addAttribute("memberNo", currentInfo.get("memberNo"));
		model.addAttribute("memberName", currentInfo.get("memberName"));
		model.addAttribute("availableDate", availableDate);
		model.addAttribute("addedScheduleMemberList", addedScheduleMemberList);
		
		return "groupMain";
	}
	
	/**
	 * 파일 다운로드
	 * @param fileNo
	 * @param response
	 */
	@RequestMapping(value = "/fileDownload", method=RequestMethod.GET)
	public void fileDownload(@RequestParam("fno") String fileNo, HttpServletResponse response){
		Map<String, Object> fileInfo = null;	
		FileUtil fileUtil = new FileUtil();
		
		fileInfo = fileService.getFilePath(fileNo);
		
		try {
			fileUtil.fileDownload(fileInfo, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 파일 업로드
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/group/{groupNo}/fileUpload", method=RequestMethod.POST)
	public String fileUpload(@PathVariable("groupNo") int groupNo, HttpServletRequest request, HttpServletResponse response, Authentication auth){
		FileUtil fileUtil = new FileUtil();
		
		List<Map<String, Object>> fileInfoList;
		
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request; 
		fileInfoList = fileUtil.fileUpload(mhsr);
		
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		fileService.insertFileInfo(fileInfoList, groupNo, u.getMemberNo());			
	
		return "redirect:/group/" + groupNo;
	}
	
	@RequestMapping(value = "/fileUpload/progress", method = RequestMethod.POST)
	public void uploadProgress(
	        HttpSession session
	        ,HttpServletResponse response) {
	 
		JSONObject jsonResult = null;
	    Object uploadInfo = session.getAttribute("UPLOAD_INFO_PREFIX");
	    if(uploadInfo != null)
	    {
	        jsonResult = (JSONObject)uploadInfo;
	    }
	    else
	    {
	        jsonResult = new JSONObject();
	        jsonResult.put("pBytesRead", 0);
	        jsonResult.put("pContentLenght", 0);
	    }
	 
	    try {
	        String jsonStr = jsonResult.toJSONString();
	 
	        response.setContentType("text/xml; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println(jsonStr);
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	@RequestMapping(value = "/group/{groupNo}/deleteGroup")
	public String groupDelete(@PathVariable("groupNo") int groupNo){
		int result = groupService.deleteGroup(groupNo);
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/group/{groupNo}/deleteGroupMember")
	public String groupMemberDelete(@PathVariable("groupNo") int groupNo, Authentication auth){
		Map<String, Object> deleteMemberInfo = new HashMap<String, Object>();
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		deleteMemberInfo.put("memberNo", u.getMemberNo());
		deleteMemberInfo.put("groupNo", groupNo);
		int result = groupService.deleteGroupMember(deleteMemberInfo);
		return "redirect:/main";
	}
	
}
