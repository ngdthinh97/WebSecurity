package com.spring.security.auth.controller.es;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.model.ArticleModel;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.service.es.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    // Article api --------------------------------------------------->

    @GetMapping("/test")
    public ResponseEntity<String> findTest() {

        ResponseEntity<String> test = articleService.test();


        return null;
        
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Article>> findAllArticle(@RequestBody Paging paging) {
        return new ResponseEntity<>(articleService.findAll(paging), HttpStatus.OK);
    }

//	@GetMapping("/findByName")
//	public ResponseEntity<Article> findAuthorByName(@RequestBody AuthorModel authorModel) {
//		return new ResponseEntity<>(authorService.findByAuthorByName(authorModel), HttpStatus.OK);
//	}

    @PostMapping("/create_article")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleModel articleModel) {
        return new ResponseEntity<>(articleService.createArticle(articleModel), HttpStatus.OK);
    }

    @PostMapping("/update/{id}/id")
    public ResponseEntity<Article> updateAuthorById(@RequestBody AuthorModel authorModel, @RequestParam String id) {
        return new ResponseEntity<>(articleService.updateAuthorById(authorModel, id), HttpStatus.OK);
    }

    @PostMapping("/update/{name}/name")
    public ResponseEntity<Article> updateAuthorByName(@RequestBody AuthorModel authorModel, @PathVariable String name) {
        return new ResponseEntity<>(articleService.updateAuthorByName(authorModel, name), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}/article")
    public ResponseEntity<String> deleteArticle(@PathVariable String id) {
        articleService.deleteArticleById(id);
        return new ResponseEntity<>("Delete Article id : " + id, HttpStatus.OK);
    }
}
