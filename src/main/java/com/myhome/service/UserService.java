package com.myhome.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myhome.dto.UserDTO;
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
		role.setId(1); // 유저의 role 테이블에 id가 1번인 권한(ROLE_USER)을 하드코딩으로 설정
		user.getRoles().add(role); // 유저의 role 추가
		
		return userRepository.save(user);

	}
	
	// 회원정보 수정시 
	public void update(long id,User user) {
		
		Optional<User> u = userRepository.findById(id);
		
		if(u.isPresent()) {
			u.get().setId(user.getId());
			u.get().setUsername(user.getUsername());
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			u.get().setPassword(encodedPassword);
			
			u.get().setAddr1(user.getAddr1());
			u.get().setAddr2(user.getAddr2());
			u.get().setAddr3(user.getAddr3());
			
			u.get().setEnabled(true);
			
			userRepository.save(u.get());
		}
		
	}
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
	
	public boolean checkUsername(String username) {
		
		return userRepository.existsByUsername(username);
	}
	
	

}
