package com.daou.moyeo.group.service;

import java.util.List;
import java.util.Map;

public interface GroupService {
	public int createGroup(Map<String, String> groupInfo);
	public List<Map<String, Object>> selectGroupList(int memberNo);
}
