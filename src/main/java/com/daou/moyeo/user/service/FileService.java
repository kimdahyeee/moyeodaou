package com.daou.moyeo.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("fileService")
public class FileService extends SqlSessionDaoSupport{

	public List<Map<String, Object>> getFileList(int groupNo){
		return getSqlSession().selectList("file.selectAllFileList", groupNo);
	}
	
	public Map<String, Object> getFilePath(String fno){
		return getSqlSession().selectOne("file.selectFilePath", fno);
	}
	
	public void insertFileInfo(List<Map<String, Object>> param, int groupNo, int memberNo){
		Map<String, Object> map;
		
		for(int i=0;i<param.size();i++){
			map = param.get(i);						
			map.put("groupNo", groupNo);
			map.put("memberNo", memberNo);
			getSqlSession().insert("file.insertFile", map);
		}	
	}
	
}
