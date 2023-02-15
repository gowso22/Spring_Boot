package com.myhome.controller;

import java.awt.print.Pageable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhome.model.Board;
import com.myhome.model.Item;
import com.myhome.repository.BoardRepository;
import com.myhome.repository.ItemRepository;
import com.myhome.service.BoardService;
import com.myhome.validator.BoardValidator;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired // 의존성 주입
	private BoardRepository boardRepository;
	
	@Autowired // 의존성 주입
	private BoardService boardService;
	@Autowired
	private BoardValidator boardValidator;
	@Autowired
	private ItemRepository itemRepository;
	
	
	@GetMapping("/list") // 게시판 메뉴 클릭 시 처음 뿌려지는 목록 화면으로 이동
	public String list(Model model, @PageableDefault(size = 2) org.springframework.data.domain.Pageable pageable, 
				@RequestParam(required = false, defaultValue = "") String searchText) {
		// board 테이블 데이터를 다 가지고 오다.
		// PageRequest.of 첫번째 파라미터 : 페이지, 두번째파라미터 : 1페이지에 보여주는 데이터 수 // 페이지0값이 첫번째 페이지
		// Page<Board> boards = boardRepository.findAll(pageable);
		
		
		// 
		Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
		// 스타트페이지의 최소값은 0페이지
		// 엔드페이지의 최대값은 전체 끝페이지
		int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4 );
		int endPage = Math.min(boards.getTotalPages(),  boards.getPageable().getPageNumber() + 4 );
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// 게시판 전체 데이터 건수 확인 메서드 >>boards.getTotalElements();
		model.addAttribute("boards", boards); // boards 값을 키-값 형태로 추가
		return "board/list";
	}

	@GetMapping("/form") // 글 조회할 id가 있다면,
	// @RequestParam(required = false) Long id >> id 파라미터값을 가져옴(required >> 새글을 작성할
	// 땐 파라미터가 필요 없으므로 false값을 줌)
	public String form(Model model, @RequestParam(required = false) Long id) {

			// findById(id) >> id 변수값이 select문의 where 절에 들어간다고 생각
			// orElse(null) >> 조회된 값이 없다면 null로 반환
			Board board = boardRepository.findById(id).orElse(null);
			model.addAttribute("board", board);
		

		return "board/form";
	}
	
	@GetMapping("/write") // 새글 작성
	public String write(Model model, @RequestParam(required = false) Long itemId) {
		
		Item item = itemRepository.findById(itemId).orElse(null);
		
		model.addAttribute("item", item);
		
		model.addAttribute("board", new Board());
		
		return "board/write";
	}
	

	@PostMapping("/form")
	public String postForm(@Valid Board board, @RequestParam(required = false) Long itemId, BindingResult bindingResult, Authentication authentication) {

		boardValidator.validate(board, bindingResult); // 따로 유효성검사 클래스를 생성하여 유효성검사 구현

		if (bindingResult.hasErrors()) {
			return "board/form";
		} // 스프링 부트 기능으로 유효성검사 구현
		
		// 인증된 username을 가져옴
		String username = authentication.getName();
		
		// 게시판글 작성한 것이 board에 save(insert 혹은 update)됨
		boardService.save(username, itemId,board);
		//boardRepository.save(board);
		
		// redirect:board/list >> list페이지 재조회
		return "redirect:/board/list";

	}
	
	
	
	
}
