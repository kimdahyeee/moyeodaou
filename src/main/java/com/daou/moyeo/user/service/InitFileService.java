package com.daou.moyeo.user.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("initFileService")
public class InitFileService extends SqlSessionDaoSupport{
	/* 공유 파일 목록 출력*/
	public List<Map<String, Object>> getFileList(){
		return getSqlSession().selectList("testQuery.showAllFile");
	}
	
}
