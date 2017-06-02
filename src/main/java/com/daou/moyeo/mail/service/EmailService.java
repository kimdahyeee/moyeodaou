package com.daou.moyeo.mail.service;

import java.util.List;
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
	 * 인증 Token 값 확인 메소드
	 * */
	public boolean checkToken(String token){
		int returnValue;
		returnValue = (getSqlSession().selectOne("email.selectToken", token));
		
		if(returnValue == 1){				// 유일한 인증code 존재
			return true;
		}else{								// 없거나 혹은 다수개??(에러) false
			System.out.println("testQuery.getCountCode return value is NOT '1' ");
			return false;
		
		}
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
	 * 	인증 Token 값 생성 및 CODE_TB에 추가해주는 메소드
	 * */
	public String createToken(){
		String token = getRandomName();
		getSqlSession().insert("email.insertToken", token);
		return token;
	}
	
	
}
