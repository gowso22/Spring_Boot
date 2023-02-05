package com.myhome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myhome.model.Board;
import com.myhome.model.User;
import com.myhome.repository.UserRepository;


@RestController
@RequestMapping("/api")

class UserAPIController {

	@Autowired
	private UserRepository repository;

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/users")
	List<User> all(){
		
		List<User> users = repository.findAll();
		
		return users;
	}
	// end::get-aggregate-root[]

	@PostMapping("/users")
	User newuser(@RequestBody User newuser, @PathVariable long id) {
		return repository.save(newuser);
	}

	// Single item
	@GetMapping("/users/{id}")
	User one(@PathVariable Long id) {

		return repository.findById(id).orElse(null);
	}

	@PutMapping("/users/{id}")
	User replaceuser(@RequestBody User newuser, @PathVariable Long id) {

		return repository.findById(id)
				.map(user -> {
//			user.setTitle(newuser.getTitle());
//			user.setContent(newuser.getContent());
			user.setBoards(newuser.getBoards());
			for(Board board : user.getBoards()) {
				board.setUser(user);
			}
			return repository.save(user);
		}).orElseGet(() -> {
			newuser.setId(id);
			return repository.save(newuser);
		});
	}

	@DeleteMapping("/users/{id}")
	void deleteuser(@PathVariable Long id) {
		repository.deleteById(id);
	}
}