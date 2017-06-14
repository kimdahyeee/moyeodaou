package com.daou.moyeo.group.service;

import java.util.List;
import java.util.Map;

public interface GroupService {
	public int insertGroup(Map<String, Object> groupInfo);
	public int insertMemberGroup(Map<String, Object> memberInfo);
	public List<Map<String, Object>> selectGroupList(int memberNo);
	public int selectGroupNo(String groupName);
	public Map<String, Object> selectGroupInfo(int groupNo); // Daeho 2017.06.07 chat
	// Daeho 2017.06.07 select group list except for current joined group
	public List<Map<String, Object>> selectOtherGroupList(Map<String, Object> currentInfo); 
	public List<Map<String, Object>> selectGroupMemberList(Map<String, Object> currentInfo); 
	public int deleteGroup(int groupNo);
	public int deleteGroupMember(Map<String, Object> deleteMemberInfo);
}
