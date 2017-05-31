package com.daou.moyeo.user.dao;

import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends SqlSessionDaoSupport implements UserService {

	@Override
	public int insertUser(Map<String, String> paramMap) {
		return getSqlSession().insert("user.insertUser", paramMap);
	}
	
}
