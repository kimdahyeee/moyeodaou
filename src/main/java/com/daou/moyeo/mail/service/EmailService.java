package com.daou.moyeo.mail.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService extends SqlSessionDaoSupport{
	
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
	public String createToken(int memberNo, int groupNo){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String token = getRandomName();
		map.put("memberNo", memberNo);
		map.put("groupNo", groupNo);
		map.put("token", token);
		
		getSqlSession().insert("email.insertToken", map);
		return token;
	}
	/*
	 * 	인증 Token 값 생성 및 CODE_TB에 추가해주는 메소드 (비회원)
	 * */
	public String createToken(int groupNo){
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getRandomName();
		
		map.put("groupNo", groupNo);
		map.put("token", token);
		
		getSqlSession().insert("email.insertTokenNonMember", map);
		return token;
	}
	/*
	 * 회원의 그룹가입 삽입해주는 메소드
	 * */
	public void putNewMemberInGroup(Map<String, Object> map){
		getSqlSession().insert("email.insertMemberGroupTB", map);
	}
	
}
