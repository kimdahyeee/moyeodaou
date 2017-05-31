package com.daou.moyeo.board.dao;

import java.util.List;
import java.util.Map;

public interface BoardService {
	public int insertBoard(Map<String, Object> paramMap);
	public Map<String, Object> selectBoardDetail(int boardNo);
	public int deleteBoard(int boardNo);
	public int updateBoard(int board_no, String contents);
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map);
	public int selectBoardCount(int groupNo);
	public List<Map<String, Object>> selectMainBoardList(int groupNo);
}
