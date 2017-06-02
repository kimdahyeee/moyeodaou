package com.daou.moyeo.mail.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService extends SqlSessionDaoSupport{
	/* DB에 인증 코드 확인 요청*/
	public boolean checkCode(String code){
		int return_value;
		return_value = (getSqlSession().selectOne("testQuery.getCountCode", code));
		
		if(return_value == 1){				// 유일한 인증code 존재
			return true;
		}else{								// 없거나 혹은 다수개??(에러) false
			System.out.println("testQuery.getCountCode return value is NOT '1' ");
			return false;
		
		}
	}
	
}
