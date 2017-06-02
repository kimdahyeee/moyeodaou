package com.daou.moyeo.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.daou.moyeo.user.vo.UserDetailsVO;

public class UserAuthenticationService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);
	
	private SqlSessionTemplate sqlSession;
	
	public UserAuthenticationService() {
		// TODO Auto-generated constructor stub
	}

	public UserAuthenticationService(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// group authority 권한 추가해야함~!!!!
		Map<String, Object> user = sqlSession.selectOne("user.selectUser", username);
		
		if(user == null ) throw new UsernameNotFoundException(username);
		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		gas.add(new SimpleGrantedAuthority(user.get("authority").toString()));
		
		//gas.add(new SimpleGrantedAuthority("권한1"));
		
		return new UserDetailsVO(user.get("username").toString(), user.get("password").toString(), true, true, true, true, gas, (Integer)user.get("memberNo"));
	}
}
