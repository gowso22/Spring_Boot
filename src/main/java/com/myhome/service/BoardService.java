package com.myhome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhome.model.Board;
import com.myhome.model.User;
import com.myhome.repository.BoardRepository;
import com.myhome.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	public Board save(String username, Board board) {
		//해당하는 username을 찾아
		User user =  userRepository.findByUsername(username);
		// board 테이블에 set
		board.setUser(user);
		
		return boardRepository.save(board);
	}

}
