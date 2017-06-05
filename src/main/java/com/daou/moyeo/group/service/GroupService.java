package com.daou.moyeo.group.service;

import java.util.List;
import java.util.Map;

public interface GroupService {
	public int insertGroup(Map<String, Object> groupInfo);
	public int insertMemberGroup(Map<String, Object> memberInfo);
	public List<Map<String, Object>> selectGroupList(int memberNo);
	
}
