package com.myhome.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhome.model.Board;
import com.myhome.repository.BoardRepository;
import com.myhome.validator.BoardValidator;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired // 의존성 주입
	private BoardRepository boardRepository;
	@Autowired
	private BoardValidator boardValidator; 

	@GetMapping("/list")
	public String list(Model model) {
		List<Board> boards = boardRepository.findAll(); // board 테이블 데이터를 다 가지고 오다.
		model.addAttribute("boards", boards); // boards 값을 키-값 형태로 추가
		return "board/list";
	}

	@GetMapping("/form")
	// @RequestParam(required = false) Long id >> id 파라미터값을 가져옴(required >> 새글을 작성할 땐 파라미터가 필요 없으므로 false값을 줌) 
	public String form(Model model, @RequestParam(required = false) Long id) {
		
		if(id == null) { // 새글 이라면
			// new Board
			model.addAttribute("board", new Board());
		}else { // 글 조회할 id가 있다면, 기존의 board 조회 후 board 값에 저장, 그 외의 경우도 포함
			// findById(id) >> id 변수값이 select문의 where 절에 들어간다고 생각
			// orElse(null) >> 조회된 값이 없다면 null로 반환 
			Board board = boardRepository.findById(id).orElse(null); 
			model.addAttribute("board", board);
		}
		
		return "board/form";
	}

	@PostMapping("/form")
	public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
		
		boardValidator.validate(board, bindingResult); // 따로 유효성검사 클래스를 생성하여 유효성검사 구현
		
		if(bindingResult.hasErrors()) {
			return "board/form";
		} // 스프링 부트 기능으로 유효성검사 구현
		
		
		// 게시판글 작성한 것이 board에 save(insert 혹은 update)됨
		boardRepository.save(board);
		// redirect:board/list >> list페이지 재조회
		return "redirect:/board/list";

	}
}
