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
	}

	public UserAuthenticationService(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Map<String, Object> user = sqlSession.selectOne("user.selectUser", username);
		
		if(user == null ) throw new UsernameNotFoundException(username);
		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		gas.add(new SimpleGrantedAuthority(user.get("authority").toString()));
		
		int memberNo = (Integer) user.get("memberNo");
		List<Map<String, Object>> groupAuth = sqlSession.selectList("user.selectGroupAuthInfo", memberNo);
		
		if(groupAuth.size() != 0){
			for(int i=0; i<groupAuth.size(); i++){
				gas.add(new SimpleGrantedAuthority("ROLE_GROUP" + groupAuth.get(i).get("groupNo") + "_" + groupAuth.get(i).get("groupAuthority") ));
			}
		}
		
		return new UserDetailsVO(user.get("username").toString(), user.get("memberName").toString(), user.get("password").toString(), gas, (Integer)user.get("memberNo"));
	}
}
