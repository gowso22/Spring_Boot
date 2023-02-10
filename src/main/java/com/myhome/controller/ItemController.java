package com.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhome.dto.ItemFormDTO;

@Controller
@RequestMapping("/item")
public class ItemController {

	@GetMapping("/itemForm")
	public String itemForm(Model model) {
		
		ItemFormDTO itemform = new ItemFormDTO();
	
		
		
		model.addAttribute("itemFormDTO", itemform);
		
		return "item/itemForm";
	}
	
}
