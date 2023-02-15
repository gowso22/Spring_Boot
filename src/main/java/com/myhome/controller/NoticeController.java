package com.myhome.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhome.model.Board;
import com.myhome.model.Notice;
import com.myhome.repository.NoticeRepository;
import com.myhome.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private NoticeService noticeService;
	
	
	@GetMapping("/list")
	public String list(Model model){
		
		List<Notice> notices =  noticeRepository.findByOrderByIdDesc();
		
		model.addAttribute("notices", notices);
		
		return "notice/list";
	}
	@GetMapping("/form") // 글 조회할 id가 있다면,
	// @RequestParam(required = false) Long id >> id 파라미터값을 가져옴(required >> 새글을 작성할
	// 땐 파라미터가 필요 없으므로 false값을 줌)
	public String form(Model model, @RequestParam(required = false) Long id) {

			// findById(id) >> id 변수값이 select문의 where 절에 들어간다고 생각
			// orElse(null) >> 조회된 값이 없다면 null로 반환
			Notice notice = noticeRepository.findById(id).orElse(null);
			model.addAttribute("notice", notice);
		

		return "notice/form";
	}
	
	@GetMapping("/write") // 새글 작성
	public String write(Model model) {
		
		model.addAttribute("notice", new Notice());
		
		return "notice/write";
	}
	
	@PostMapping("/form")
	public String postForm(@Valid Notice notice, BindingResult bindingResult, Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return "notice/form";
		} // 스프링 부트 기능으로 유효성검사 구현
		
		// 인증된 username을 가져옴
		String username = authentication.getName();
		
		// 게시판글 작성한 것이 board에 save(insert 혹은 update)됨
		noticeService.save(username, notice);
		//boardRepository.save(board);
		
		// redirect:board/list >> list페이지 재조회
		return "redirect:/notice/list";

	}
	
	//@Secured("ROLE_ADMIN") // ADMIN 사용자만 해당 메서드 호출 >> MethodSecurityConfig 페이지에서 참조
	@GetMapping("/delnotice")
	public String deletenotice(@RequestParam(required = false) Long id) {
		
		noticeRepository.deleteById(id);
		return "redirect:/notice/list";
	}
	
	
	
}
