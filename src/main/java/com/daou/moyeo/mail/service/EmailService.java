package com.daou.moyeo.mail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.daou.moyeo.user.vo.UserDetailsVO;

@Service("emailService")
public class EmailService extends SqlSessionDaoSupport{
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, String, String> hashOps;
	
	@Resource(name="redisTemplate")
	private SetOperations<String, String> setOps;
	
	@Resource(name="redisTemplate")
	private ListOperations<String, String> listOps;
	
	private static String getRandomName(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
	/* 
	 * 그룹No, 멤버No, 인증 Token 값 확인 메소드
	 * */
	public boolean checkToken(Map<String, Object> map){
		int returnValue;
		returnValue = getSqlSession().selectOne("email.selectToken", map);
		
		if(returnValue == 1){							
			return true;
		}else{										
			return false;
		}
	}
	/*
	 *  ID로 멤버 No 가져오는 메소드
	 * */
	
	public Integer getMemberNo(String receiverEmail){
		int receiverNo;
		receiverNo = (getSqlSession().selectOne("email.selectMemberNo", receiverEmail));
		
		return receiverNo;
	}
	/*
	 	회원 인지 아닌지 확인하는 메소드	
	 */
	public boolean checkMemberOrNot(String receiverEmail){
		int returnValue;
		returnValue = (getSqlSession().selectOne("email.selectMemberOrNot", receiverEmail));
		
		if(returnValue == 1){							
			return true;
		}else{										
			return false;
		}
	}
	/*
	 * 같은 그룹에서 온 초대인지 아닌지 확인하는 메소드
	 * */
	public boolean checkTheSameInvitedGroupOrNot(Map<String, Object> map){
		int returnValue;
		
		returnValue = (getSqlSession().selectOne("email.selectTheSameInvitedGroupOrNot", map));
		if(returnValue == 0)
			return false;
		else
			return true;
	}
	/*
	 * 	인증 Token 값 생성 및 CODE_TB에 추가해주는 메소드 (회원)
	 * */
	public String createToken(int memberNo, int groupNo, String email){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> rmap = new HashMap<String, String>();
		
		String token = getRandomName();
		map.put("memberNo", memberNo);
		map.put("groupNo", groupNo);
		map.put("token", token);
		map.put("email", email);
		
		rmap.put("memberNo", Integer.toString(memberNo));
		rmap.put("groupNo", Integer.toString(groupNo));
		rmap.put("token", token);
		rmap.put("email", email);
		
		System.out.println(token);
		
		hashOps.putAll(token, rmap);
		redisTemplate.expire(token, 5, TimeUnit.MINUTES);
		
		System.out.println("===============================================");
		
		System.out.println("test" +hashOps.get(token, "email"));
		
		return token;
	}
	/*
	 * 	인증 Token 값 생성 및 CODE_TB에 추가해주는 메소드 (비회원)
	 * */
	public String createToken(int groupNo, String email){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> rmap = new HashMap<String, String>();
		
		String token = getRandomName();
		
		map.put("groupNo", groupNo);
		map.put("token", token);
		
		rmap.put("memberNo", Integer.toString(-1));
		rmap.put("groupNo", Integer.toString(groupNo));
		rmap.put("token", token);
		rmap.put("email", email);
		
		System.out.println(token);
		
		hashOps.putAll(token, rmap);
		redisTemplate.expire(token, 5, TimeUnit.MINUTES);
		
		System.out.println("===============================================");
		System.out.println("test" +hashOps.get(token, "email"));
		
		return token;
	}
	/*
	 * 회원의 그룹가입 삽입해주는 메소드
	 * */
	public void putNewMemberInGroup(Map<String, Object> map){
		getSqlSession().insert("email.insertMemberGroupTB", map);
	}
	
}
