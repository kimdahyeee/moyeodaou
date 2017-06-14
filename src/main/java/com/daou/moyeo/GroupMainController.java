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
import com.daou.moyeo.user.service.FileService;
import com.daou.moyeo.user.vo.UserDetailsVO;
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
	
	/**
	 *  그룹 메인 화면
	 * @param model
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/group/{groupNo}")
	public String groupMainInit(@PathVariable("groupNo") int groupNo, Model model, Authentication auth) {		
		
		//TODO 그룹 권한 삽입
		System.out.println("Group Main Init()");
		
		//Daeho 2017.06.07 chat
		Map<String, Object> currentInfo = new HashMap<String, Object>();
		UserDetailsVO u = (UserDetailsVO)auth.getPrincipal();
		currentInfo.put("memberNo", u.getMemberNo());
		currentInfo.put("memberName", u.getMemberName());
		currentInfo.put("groupNo", groupNo);
		
		List<Map<String, Object>> sharingList = fileService.getFileList(groupNo);  // load Group Fille List
		List<Map<String, Object>> allMainBoardList = boardService.selectMainBoardList(groupNo); // load Group Board List
		
		Map<String, Object> groupInfo = groupService.selectGroupInfo(groupNo); // daeho 2017.06.07 chat
		List<Map<String, Object>> otherGroupList = groupService.selectOtherGroupList(currentInfo); // daeho 2017.06.07 chat
		List<Map<String, Object>> groupMemberList = groupService.selectGroupMemberList(currentInfo);
		
		//=============== FileList Test ===================//
		/*for(int i=0;i<sharing_list.size();i++){
			Map<String, Object> map;		
			map = sharing_list.get(i);						
			System.out.println(map.get("file_name") + "," + map.get("member_no") + "," + map.get("group_file_no"));
		}	*/
		
		model.addAttribute("sharingList", sharingList);
		model.addAttribute("allMainBoardList", allMainBoardList);
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("groupInfo", groupInfo); // daeho 2017.06.07 chat
		model.addAttribute("otherGroupList", otherGroupList); 
		model.addAttribute("groupMemberList", groupMemberList); 
		model.addAttribute("memberNo", currentInfo.get("memberNo"));
		model.addAttribute("memberName", currentInfo.get("memberName"));
		
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
			System.out.println("file download error");
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
		
		System.out.println("GroupmainController mapping fileUpload");
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request; 
		fileInfoList = fileUtil.fileUpload(mhsr);
		
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		fileService.insertFileInfo(fileInfoList, groupNo, u.getMemberNo());			
	
		return "redirect:/group/" + groupNo;
	}
	
	@RequestMapping(value = "/group/{groupNo}/deleteGroup")
	public String groupDelete(@PathVariable("groupNo") int groupNo){
		int result = groupService.deleteGroup(groupNo);
		System.out.println("result : "  + result);
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/group/{groupNo}/deleteGroupMember")
	public String groupMemberDelete(@PathVariable("groupNo") int groupNo, Authentication auth){
		Map<String, Object> deleteMemberInfo = new HashMap<String, Object>();
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		deleteMemberInfo.put("memberNo", u.getMemberNo());
		deleteMemberInfo.put("groupNo", groupNo);
		int result = groupService.deleteGroupMember(deleteMemberInfo);
		System.out.println("result : "  + result);
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/group/{groupNo}/calendar")
	public String calendarView(@PathVariable("groupNo") int groupNo, Model model) {
		model.addAttribute("groupNo", groupNo);
		return "calendar";
	}
	
}
