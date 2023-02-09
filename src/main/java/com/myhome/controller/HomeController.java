package com.myhome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myhome.model.Board;
import com.myhome.repository.BoardRepository;

@Controller
public class HomeController {

	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping
	public String index(Model model, @PageableDefault(size = 3) Pageable pageable) {
		
		Page<Board> boards = boardRepository.findAll(pageable);
		
		model.addAttribute("boards", boards); // boards 값을 키-값 형태로 추가
		
		
		return "index";
	}

}
