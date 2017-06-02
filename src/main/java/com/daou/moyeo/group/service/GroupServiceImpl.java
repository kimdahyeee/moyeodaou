package com.daou.moyeo.group.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("groupService")
public class GroupServiceImpl extends SqlSessionDaoSupport implements GroupService {

	@Override
	public int createGroup(Map<String, String> groupInfo) {
		return getSqlSession().insert("group.insertGroupInfo", groupInfo);
	}

	@Override
	public List<Map<String, Object>> selectGroupList(int memberNo) {
		return getSqlSession().selectList("group.selectGroupList", memberNo);
	}

}
