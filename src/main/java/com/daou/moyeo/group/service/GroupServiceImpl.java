package com.daou.moyeo.group.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("groupService")
public class GroupServiceImpl extends SqlSessionDaoSupport implements GroupService {

	@Override
	public int insertGroup(Map<String, Object> groupInfo) {
		return getSqlSession().insert("group.insertGroup", groupInfo);
	}

	@Override
	public int insertMemberGroup(Map<String, Object> memberInfo) {
		return getSqlSession().insert("group.insertMemberGroup", memberInfo);
	}

	@Override
	public List<Map<String, Object>> selectGroupList(int memberNo) {
		return getSqlSession().selectList("group.selectGroupList", memberNo);
	}

	@Override
	public int selectGroupNo(String groupName) {
		return getSqlSession().selectOne("group.selectGroupNo", groupName);
	}
	
	// Daeho 2016.06.07 chat
	@Override
	public Map<String, Object> selectGroupInfo(int groupNo){
		return getSqlSession().selectOne("group.selectGroupInfo", groupNo);
	}
	
	@Override
	public List<Map<String, Object>> selectOtherGroupList(Map<String, Object> currentInfo) {
		return getSqlSession().selectList("group.selectOtherGroupList", currentInfo);
	}

	@Override
	public List<Map<String, Object>> selectGroupMemberList(Map<String, Object> currentInfo) {
		return getSqlSession().selectList("group.selectGroupMemberList", currentInfo);
	}

	@Override
	public void deleteGroup(int groupNo) {
		getSqlSession().delete("group.deleteGroup", groupNo);
	}

	@Override
	public int deleteGroupMember(Map<String, Object> deleteMemberInfo) {
		return getSqlSession().delete("group.deleteGroupMember", deleteMemberInfo);
	}
	
	
}
