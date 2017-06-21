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

import com.daou.moyeo.board.service.BoardService;
import com.daou.moyeo.dto.PagingDTO;
import com.daou.moyeo.user.vo.UserDetailsVO;

@Controller
@RequestMapping(value = "/group/{groupNo}")
public class BoardController {

	@Resource(name="boardService")
	private BoardService boardService;
	
	/**
	 * 게시판 리스트
	 * @param currentPageNo
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/boardList", method=RequestMethod.GET )
	public String boardList( @PathVariable("groupNo") int groupNo, 
										@RequestParam(value="page", required=false) String currentPageNo, 
										Model model ) {
		PagingDTO pagingVO = new PagingDTO();
		
		if(StringUtils.isEmpty(currentPageNo) == true) {
			pagingVO.setCurrentPageNo(1);
		}else {
			pagingVO.setCurrentPageNo(Integer.parseInt(currentPageNo));
		}
		
		int firstRecordIndex = pagingVO.getFirstRecordIndex();
		int recordCountPerPage = pagingVO.getRecordCountPerPage();
		
		Map<String, Object> pageInfoMap = new HashMap<String, Object>();
		
		pageInfoMap.put("firstIndex", firstRecordIndex);
		pageInfoMap.put("recordCountPerPage", recordCountPerPage);
		pageInfoMap.put("groupNo", groupNo);
		
		List<Map<String, Object>> allBoardList = boardService.selectBoardList(pageInfoMap);
		
		if(allBoardList.size() != 0) {
			System.out.println("total  : " + allBoardList.get(0).get("totalCount"));
			pagingVO.setTotalRecordCount((Integer) allBoardList.get(0).get("totalCount"));
		}else{
			pagingVO.setTotalRecordCount(0);
		}
		
		model.addAttribute("paginationInfo", pagingVO);
		model.addAttribute("allBoardList", allBoardList);
		model.addAttribute("groupNo", groupNo);
		
		return "boardList";
	}
	
	/**
	 * 게시판 상세보기
	 * @param boardNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/boardDetail/{boardNo}")
	public String boardDetail( @PathVariable("groupNo") int groupNo, @PathVariable("boardNo") int boardNo, Model model) {
		Map<String, Object> boardDetailMap = boardService.selectBoardDetail(boardNo);
		model.addAttribute("boardDetailMap", boardDetailMap);
		model.addAttribute("groupNo", groupNo);
		return "boardDetail";
	}
	
	
	/**
	 * 게시글 쓰기 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/boardWrite")
	public String boardWrite(@PathVariable("groupNo") int groupNo, Model model) {
		model.addAttribute("groupNo", groupNo);
		return "boardWrite";
	}
	
	/**
	 * 게시글 쓰기
	 * @param title
	 * @param contents
	 * @param auth
	 * @return
	 */
	@RequestMapping(value ="/insertBoard", method=RequestMethod.POST)
	public String insertBoard( @PathVariable("groupNo") int groupNo,
										   @RequestParam("title") String title,
										   @RequestParam("contents") String contents,
										   Authentication auth){
		Map<String, Object> boardMap = new HashMap<String, Object>();
		boardMap.put("title", title);
		boardMap.put("contents", contents);
		boardMap.put("groupNo", groupNo);
		
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		boardMap.put("author", u.getMemberNo());
		
		boardService.insertBoard(boardMap);
		return "redirect:/group/"+groupNo+"/boardList";
	}
	
	/**
	 * 게시글 수정 화면
	 * @param boardIDX
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detailBoard/{boardNo}/update")
	public String boardUpdate( @PathVariable("groupNo") int groupNo, @PathVariable("boardNo") int boardNo, Model model ) {
		Map<String, Object> boardDetailMap = boardService.selectBoardDetail(boardNo);
		model.addAttribute("boardDetailMap", boardDetailMap);
		return "boardUpdate";
	}
	
	/**
	 * 게시글 수정
	 * @param boardIDX
	 * @param contents
	 * @return
	 */
	@RequestMapping(value ="/updateBoard/{boardNo}", method=RequestMethod.POST)
	public String updateBoard( @PathVariable("groupNo") int groupNo, @PathVariable("boardNo") int boardNo,@RequestParam("contents") String contents){
		boardService.updateBoard(boardNo, contents);
		return  "redirect:/group/"+groupNo+"/boardList";
	}
	
	/**
	 * 게시글 삭제
	 * @param boardIDX
	 * @return
	 */
	@RequestMapping(value ="/deleteBoard/{boardNo}")
	public String insertBoard( @PathVariable("groupNo") int groupNo, @PathVariable("boardNo") int boardNo){
		boardService.deleteBoard(boardNo);
		return  "redirect:/group/"+groupNo+"/boardList";
	}
	
}
