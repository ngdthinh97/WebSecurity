package com.spring.security.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleModel {

	private String title;

	private ArticleModel.Author author;

	@Getter
	@Setter
	public static class Author {
		private String id;
		private String name;
		private String address;
	}
}
