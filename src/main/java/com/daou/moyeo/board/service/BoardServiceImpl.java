package com.daou.moyeo.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("boardService")
public class BoardServiceImpl extends SqlSessionDaoSupport implements BoardService {

	@Override
	public int insertBoard(Map<String, Object> paramMap) {
		return getSqlSession().insert("board.insertBoard", paramMap);
	}

	@Override
	public Map<String, Object> selectBoardDetail(int boardNo) {
		return getSqlSession().selectOne("board.selectBoardDetail", boardNo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return getSqlSession().delete("board.deleteBoard", boardNo);
	}

	@Override
	public int updateBoard(int board_no, String contents) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board_no", board_no);
		map.put("contents", contents);
		return getSqlSession().update("board.updateBoard", map);
	}

	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) {
		return getSqlSession().selectList("board.selectBoardList", map);
	}

	@Override
	public int selectBoardCount(int groupNo) {
		return getSqlSession().selectOne("board.selectBoardCount", groupNo);
	}

	@Override
	public List<Map<String, Object>> selectMainBoardList(int groupNo) {
		return getSqlSession().selectList("board.selectMainBoardList", groupNo);
	}
	
}
