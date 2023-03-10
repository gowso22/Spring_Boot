package com.myhome.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhome.dto.ItemSearchDTO;
import com.myhome.dto.MainItemDTO;
import com.myhome.model.Board;
import com.myhome.model.Notice;
import com.myhome.repository.BoardRepository;
import com.myhome.repository.NoticeRepository;
import com.myhome.service.ItemService;

@Controller
public class HomeController {

	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping
	public String index(Model model, Optional<Integer> page, ItemSearchDTO itemSearchDTO) {
		
		List<Notice> notices = noticeRepository.findTop3ByOrderByIdDesc();
	
		model.addAttribute("notices", notices); // boards 값을 키-값 형태로 추가
		
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		Page<MainItemDTO> items = itemService.getMainItemPage(itemSearchDTO, pageable);
		
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDTO", itemSearchDTO);
		model.addAttribute("maxPage", 2);
		
		return "index";
	}

}
