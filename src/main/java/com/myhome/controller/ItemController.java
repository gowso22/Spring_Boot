package com.myhome.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myhome.dto.ItemFormDTO;
import com.myhome.dto.ItemSearchDTO;
import com.myhome.dto.MainItemDTO;
import com.myhome.model.Item;
import com.myhome.repository.ItemRepository;
import com.myhome.service.ItemService;


import lombok.RequiredArgsConstructor;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemRepository itemRepository;
	
	// 새 상품 등록 폼으로 이동
	@GetMapping("/item/itemForm")
	public String itemForm(Model model) {

		ItemFormDTO itemform = new ItemFormDTO();

		model.addAttribute("itemFormDTO", itemform);

		return "item/itemForm";
	}
	
	// 상품 등록 시
	@PostMapping("/item/new")
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
	
	// 상품 상세페이지로 이동
	@GetMapping("/item/{itemId}")
	public String itemDetail(Model model, @PathVariable("itemId") Long itemId) {
		
		ItemFormDTO itemFormDTO = itemService.getItemDtl(itemId); 
		// itemService에서 가져온 상품상세정보 담은 메서드를 itemFormDTO 객체에 담아 전달
		model.addAttribute("item", itemFormDTO);
		
		
		return "item/itemDetail";
	}
	
	// 상품 수정페이지로 이동
	 @GetMapping(value = "/items/{itemId}") 
	    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
	    	
	        try {
	        // 해당 클릭시 상품 아이디를 가지고 메서드를 호출
	        	//결과값은 한 상품의 상세페이지내용을 가지고 옴
	        	ItemFormDTO itemFormDTO = itemService.getItemDtl(itemId);
	            // 해당 뷰에 키 to 값 형태로 model에 등록해서 전달
	        	model.addAttribute("itemFormDTO", itemFormDTO);
	        } catch(EntityNotFoundException e){
	            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
	            model.addAttribute("itemFormDTO", new ItemFormDTO());
	            return "item/itemForm";
	        }

	        return "item/itemForm";
	    }
	
	@GetMapping("/items") // 상품 관리페이지로 이동
	public String itemAdmin(ItemSearchDTO itemSearchDTO, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<MainItemDTO> items = itemService.getMainItemPage(itemSearchDTO, pageable);
		
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDTO", itemSearchDTO);
		model.addAttribute("maxPage", 2);
	        return "item/itemAdm";
	}
	
	// 상품 수정 로직
	@PostMapping("/item/edit/{itemId}")
	public String itemUpdate(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {
		
		if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
        	// 다시 상품번호로 재등록함. 일반 데이터, 파일 데이터
            itemService.updateItem(itemFormDTO, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/items";
		
	}
	
	// 상품 삭제 로직
	@GetMapping("item/delitem")
	public String deleteItem(@RequestParam(required = false) Long itemId) {
		
		itemRepository.deleteById(itemId);
		
		return "redirect:/items";
	}
	
	
	
}
