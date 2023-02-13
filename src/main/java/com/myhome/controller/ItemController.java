package com.myhome.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myhome.dto.ItemFormDTO;
import com.myhome.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@GetMapping("/itemForm")
	public String itemForm(Model model) {

		ItemFormDTO itemform = new ItemFormDTO();

		model.addAttribute("itemFormDTO", itemform);

		return "item/itemForm";
	}

	@PostMapping("/new")
	public String itemNew(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "item/itemForm"; // 유효성 검사에 맞지 않다면 다시 상품등록페이지로 전환
		}
		
		if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null) { 
			// 1번째 상품이미지가 없다면 에러메세지와 함께 상품등록페이지로 전환
			model.addAttribute("errorMessage", "1번째 상품이미지는 필수 입력 값입니다.");
			return "item/itemForm";
		}
		
		try {
			itemService.saveItem(itemFormDTO, itemImgFileList);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "상품 등록 중 에러 발생했습니다.");
			return "item/itemForm";
		}
		
		return "redirect:/";

	}
}
