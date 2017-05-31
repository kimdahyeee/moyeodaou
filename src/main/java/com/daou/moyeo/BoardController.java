package com.daou.moyeo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daou.moyeo.board.dao.BoardService;
import com.daou.moyeo.board.util.CommandMap;
import com.daou.moyeo.user.vo.UserDetailsVO;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class BoardController {

	@Resource(name="boardService")
	private BoardService boardService;
	
	@RequestMapping(value = "/boardDetail/{boardIDX}")
	public String boardDetail( @PathVariable("boardIDX") int boardIdx, Model model) {
		Map<String, Object> boardDetailMap = boardService.selectBoardDetail(boardIdx);
		model.addAttribute("boardDetailMap", boardDetailMap);
		return "boardDetail";
	}
	
	@RequestMapping(value = "/boardList")
	public String boardList( @RequestParam(value="currentPageNo", required=false) String currentPageNo, Model model ) {
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		System.out.println("currentPage No  ====> " + currentPageNo);
		
		if(StringUtils.isEmpty(currentPageNo) == true) {
			paginationInfo.setCurrentPageNo(1);
		}else {
			paginationInfo.setCurrentPageNo(Integer.parseInt(currentPageNo));
		}
		//한 페이지에 게시되는 게시물 건수
		paginationInfo.setRecordCountPerPage(10); 
		//페이징 리스트의 사이즈
		paginationInfo.setPageSize(5); 
		
		int firstRecordIndex = paginationInfo.getFirstRecordIndex();
		int recordCountPerPage = paginationInfo.getRecordCountPerPage();

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("firstIndex", firstRecordIndex);
		map.put("recordCountPerPage", recordCountPerPage);
		map.put("groupNo", 1);
		
		List<Map<String, Object>> allBoardList = boardService.selectBoardList(map);
		paginationInfo.setTotalRecordCount((Integer) allBoardList.get(0).get("totalCount"));

		System.out.println("allboardList" + allBoardList);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("allBoardList", allBoardList);
		
		return "boardList";
	}
	
	@RequestMapping(value = "/boardWrite")
	public String boardWrite(Model model) {
		return "boardWrite";
	}
	
	@RequestMapping(value = "/detailBoard/{boardIDX}/update")
	public String boardUpdate( @PathVariable("boardIDX") int boardIdx, Model model ) {
		Map<String, Object> boardDetailMap = boardService.selectBoardDetail(boardIdx);
		model.addAttribute("boardDetailMap", boardDetailMap);
		return "boardUpdate";
	}
	
	@RequestMapping(value ="/updateBoard/{board_no}", method=RequestMethod.POST)
	public String updateBoard( @PathVariable("board_no") int board_no,
			@RequestParam("contents") String contents){
		boardService.updateBoard(board_no, contents);
		return "redirect:/boardList";
	}
	
	@RequestMapping(value ="/insertBoard", method=RequestMethod.POST)
	public String insertBoard( @RequestParam("title") String title,
										   @RequestParam("contents") String contents,
										   Authentication auth){
		Map<String, Object> boardMap = new HashMap<String, Object>();
		boardMap.put("title", title);
		boardMap.put("contents", contents);
		
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		boardMap.put("author", u.getMemberNo());
		
		boardService.insertBoard(boardMap);
		return "redirect:/boardList";
	}
	
	@RequestMapping(value ="/deleteBoard/{board_no}")
	public String insertBoard( @PathVariable("board_no") int board_no){
		boardService.deleteBoard(board_no);
		return "redirect:/boardList";
	}
	
}
