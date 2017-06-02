package com.daou.moyeo.util;

import java.util.Map;

import org.springframework.util.StringUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class BoardUtil {
	/*public Map selectPagingList(String queryId, Object params){
		Map<String,Object> map = (Map<String,Object>)params;
	    PaginationInfo paginationInfo = null;
	     
	    if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
	        map.put("currentPageNo","1");
	     
	    paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
	    if(map.containsKey("PAGE_ROW") == false || StringUtils.isEmpty(map.get("PAGE_ROW")) == true){
	        paginationInfo.setRecordCountPerPage(15);
	    }
	    else{
	        paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("PAGE_ROW").toString()));
	    }
	    paginationInfo.setPageSize(10);
	     
	    int start = paginationInfo.getFirstRecordIndex();
	    int end = start + paginationInfo.getRecordCountPerPage();
	    map.put("START",start+1);
	    map.put("END",end);
	     
	    params = map;
	     
	    Map<String,Object> returnMap = new HashMap<String,Object>();
	    List<Map<String,Object>> list = sqlSession.selectList(queryId,params);
	     
	    if(list.size() == 0){
	        map = new HashMap<String,Object>();
	        map.put("TOTAL_COUNT",0); 
	        list.add(map);
	         
	        if(paginationInfo != null){
	            paginationInfo.setTotalRecordCount(0);
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    else{
	        if(paginationInfo != null){
	            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("TOTAL_COUNT").toString()));
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    returnMap.put("result", list);
	    return returnMap;

	}*/
}
