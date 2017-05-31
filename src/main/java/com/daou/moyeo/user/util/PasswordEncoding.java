package com.daou.moyeo.user.util;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("bcryptEncoder")
public class PasswordEncoding implements PasswordEncoder{
	
	@Resource(name="passwordEncoder")
	private PasswordEncoder passwordEncoder;
	
	public PasswordEncoding() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public PasswordEncoding(PasswordEncoder passwordEncoder){
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	/**
	 * 
	 * @param rawPassword ��ȣȭ ���� ���� ��
	 * @param encodedPassword ��ȣȯ �� ��
	 * @return
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
}
