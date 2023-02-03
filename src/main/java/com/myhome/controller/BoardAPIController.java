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
import org.thymeleaf.util.StringUtils;

import com.myhome.model.Board;
import com.myhome.repository.BoardRepository;

@RestController
@RequestMapping("/api")

class BoardAPIController {

	@Autowired
	private BoardRepository repository;

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/boards")
	
	List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
					@RequestParam(required = false, defaultValue = "") String content) {
		
		if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
			return repository.findAll();
		} else {
			return repository.findByTitleOrContent(title, content);
		}

	}
	// end::get-aggregate-root[]

	@PostMapping("/boards")
	Board newboard(@RequestBody Board newboard, @PathVariable long id) {
		return repository.save(newboard);
	}

	// Single item
	@GetMapping("/boards/{id}")
	Board one(@PathVariable Long id) {

		return repository.findById(id).orElse(null);
	}

	@PutMapping("/boards/{id}")
	Board replaceboard(@RequestBody Board newboard, @PathVariable Long id) {

		return repository.findById(id).map(board -> {
			board.setTitle(newboard.getTitle());
			board.setContent(newboard.getContent());
			return repository.save(board);
		}).orElseGet(() -> {
			newboard.setId(id);
			return repository.save(newboard);
		});
	}

	@DeleteMapping("/boards/{id}")
	void deleteboard(@PathVariable Long id) {
		repository.deleteById(id);
	}
}