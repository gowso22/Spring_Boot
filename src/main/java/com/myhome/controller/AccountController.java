package com.myhome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhome.model.User;
import com.myhome.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login()
	{
		return "account/login";
	}
	
	@GetMapping("/register")
	public String register()
	{
		return "account/register";
	}
	
	@PostMapping("/register")
	public String register(User user)
	{
		userService.save(user);
		// 로그인 시
		// HomeController에 index 부분("/">>과 동일) 메서드를 한번 더 쓰고 난 후(redirect) 페이지 이동
		return "redirect:/"; 
	}

}
