package com.myhome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
	Iterable<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {

		Iterable<User> users = repository.findAll();

//		다양한 커스텀 쿼리 예시
//		if ("query".equals(method)) { 기본 커스텀 커리
//			users = repository.findByUsernameQuery(text);
//		} else if ("nativequery".equals(method)) { native쿼리 이용
//			users = repository.findByUsernameNativeQuery(text);
//		} else if ("querydsl".equals(method)) { querydsl 이용
//			QUser user = QUser.user;
//			// contains >> like 기능과 비슷
//			Predicate predicate = user.username.contains(text);
//
//			user = (QUser) repository.findAll(predicate);
//
//		} else if ("querydslCustom".equals(method)) { dsl커스텀 쿼리 이용
//			users = repository.findByUsernameCustom(text);
//		}else if("jdbc".equals(method)) { // jdbctemplate 이용
//			users = repository.findByUsernameJdbc(text);
//		}
//		else {
//			users = repository.findAll();
//		}
		
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

		return repository.findById(id).map(user -> {
//			user.setTitle(newuser.getTitle());
//			user.setContent(newuser.getContent());
			user.setBoards(newuser.getBoards());
			for (Board board : user.getBoards()) {
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