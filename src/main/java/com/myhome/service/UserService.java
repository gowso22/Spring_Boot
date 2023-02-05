package com.myhome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myhome.model.Role;
import com.myhome.model.User;
import com.myhome.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User save(User user) {

		// 회원 가입시 비밀번호를 암호화 시켜 set
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		// 회원가입시 enabled 컬럼 활성화
		user.setEnabled(true);
		
		// 유저 회원가입 시 role 설정
		Role role = new Role();
		role.setId(2); // 유저의 role 테이블에 id가 1번인 권한(ROLE_USER)을 하드코딩으로 설정
		user.getRoles().add(role); // 유저의 role 추가
		
		return userRepository.save(user);

	}

}
