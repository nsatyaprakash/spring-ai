package com.spring.openai.model;

import java.util.List;

public class Author {

	private String author;
	private List<String> books;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getBooks() {
		return books;
	}

	public void setBooks(List<String> books) {
		this.books = books;
	}

}
