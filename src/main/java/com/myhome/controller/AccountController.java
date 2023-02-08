package com.myhome.controller;

import java.awt.Dialog.ModalExclusionType;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhome.model.User;
import com.myhome.repository.UserRepository;
import com.myhome.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/login")
	public String login() {

		return "account/login";
	}

	@GetMapping("/register")
	public String register() {
		return "account/register";
	}

	@PostMapping("/register")
	public String register(User user) {
		userService.save(user);
		// 로그인 시
		// HomeController에 index 부분("/">>과 동일) 메서드를 한번 더 쓰고 난 후(redirect) 페이지 이동
		return "redirect:/";
	}

	@GetMapping("/edit")
	public String edit(Model model, @RequestParam(required = false) String username) {
		User user = userRepository.findByUsername(username);

		model.addAttribute("user", user);

		return "account/edit";
	}

	@PostMapping("/edit")
	public String edit(@Valid User user, Authentication authentication) {
		String username = authentication.getName();
		
		userService.update(username, user);

		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable long id) {

		userService.deleteById(id);

		return "redirect:/logout";
	}

}
