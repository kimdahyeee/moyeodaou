package com.daou.moyeo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import com.daou.moyeo.board.dao.BoardService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class BoardUtil {
	@Resource(name="boardService")
	private BoardService boardService;
	
	public Map<String, Object> paging(Map<String, Integer> pagingInfo){
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		if(StringUtils.isEmpty(pagingInfo.get("currentPageNo")) == true) {
			paginationInfo.setCurrentPageNo(1);
		}else {
			paginationInfo.setCurrentPageNo(pagingInfo.get("currentPageNo"));
		}
		
		//한 페이지에 게시되는 게시물 건수
		paginationInfo.setRecordCountPerPage(10); 
		//페이징 리스트의 사이즈
		paginationInfo.setPageSize(5); 
		
		int firstRecordIndex = paginationInfo.getFirstRecordIndex();
		int recordCountPerPage = paginationInfo.getRecordCountPerPage();
		
		Map<String, Object> pageInfoMap = new HashMap<String, Object>();
		
		pageInfoMap.put("firstIndex", firstRecordIndex);
		pageInfoMap.put("recordCountPerPage", recordCountPerPage);
		pageInfoMap.put("groupNo",pagingInfo.get("groupNo") );
		
		List<Map<String, Object>> allBoardList = boardService.selectBoardList(pageInfoMap);
		
		//TODO 여기 예외처리 하기
		if(allBoardList.size() != 0) {
			System.out.println("total  : " + allBoardList.get(0).get("totalCount"));
			paginationInfo.setTotalRecordCount((Integer) allBoardList.get(0).get("totalCount"));
		}else{
			paginationInfo.setTotalRecordCount(0);
		}
		returnMap.put("allBoardList", allBoardList);
		returnMap.put("paginationInfo", paginationInfo);
		
		return returnMap;
	}
}
