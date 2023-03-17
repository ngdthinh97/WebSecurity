package com.spring.security.auth.controller.es;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.ArticleModel;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.service.es.ArticleService;
import com.spring.security.auth.service.es.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	// Author API --------------------------------------------------->

	@GetMapping
	public ResponseEntity<SearchHits<Author>> findAuthorByName(@RequestBody AuthorModel authorModel) {
		return new ResponseEntity<>(authorService.findByAuthorName(authorModel), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Author>> getListArticlesByAuthorName(@RequestBody AuthorModel authorModel) {
		return new ResponseEntity<>(authorService.findByAuthorsName(authorModel), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Author> createAuthorByName(@RequestBody AuthorModel authorModel) {
		return new ResponseEntity<>(authorService.createAuthor(authorModel), HttpStatus.OK);
	}

	@PostMapping("/{id}/update")
	public ResponseEntity<Author> updateAuthorById(
			@PathVariable String id,
			@RequestBody AuthorModel authorModel) {
		return new ResponseEntity<>(authorService.updateAuthor(id, authorModel), HttpStatus.OK);
	}
}
