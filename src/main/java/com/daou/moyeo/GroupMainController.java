package com.daou.moyeo;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.daou.moyeo.board.dao.BoardService;
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
	
	/**
	 *  그룹 메인 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/group/{groupNo}")
	public String groupMainInit(@PathVariable("groupNo") int groupNo, Model model) {		
		//TODO 그룹 권한 삽입
		System.out.println("Group Main Init()");
		
		List<Map<String, Object>> sharing_list = fileService.getFileList(groupNo);  // load Group Fille List
		List<Map<String, Object>> allMainBoardList = boardService.selectMainBoardList(groupNo); // load Group Board List
		
		//=============== FileList Test ===================//
		/*for(int i=0;i<sharing_list.size();i++){
			Map<String, Object> map;		
			map = sharing_list.get(i);						
			System.out.println(map.get("file_name") + "," + map.get("member_no") + "," + map.get("group_file_no"));
		}	*/
		
		model.addAttribute("sharing_list", sharing_list);
		model.addAttribute("allMainBoardList", allMainBoardList);
		model.addAttribute("groupNo", groupNo);
		
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
		
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request; 
		fileInfoList = fileUtil.fileUpload(mhsr);
		
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		fileService.insertFileInfo(fileInfoList, groupNo, u.getMemberNo());			
	
		return "redirect:/group/" + groupNo;
	}
	
	
}
