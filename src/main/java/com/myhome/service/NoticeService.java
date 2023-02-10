package com.myhome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhome.model.Notice;
import com.myhome.model.User;
import com.myhome.repository.NoticeRepository;
import com.myhome.repository.UserRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private UserRepository userRepository;
	
	public Notice save(String username, Notice notice) {
		
		User user = userRepository.findByUsername(username);
		
		notice.setUser(user);
		
		return noticeRepository.save(notice);
		
	}
	
	
}
