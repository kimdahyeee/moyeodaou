package com.daou.moyeo.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("fileDownloadService")
public class FileDownloadService  extends SqlSessionDaoSupport{
	
	// Get File Path ¸Þ¼Òµå
	public Map<String, Object> get_filepath_DB(String fno){
		
		Map<String, String> map = null;
		
		System.out.println("fileDownloadService.get_filepath_DB(" + fno + ")");
		map = new HashMap<String, String>();
		map.put("fno", fno);
		
		return getSqlSession().selectOne("testQuery.getFilePath", map);
	
	}

}
