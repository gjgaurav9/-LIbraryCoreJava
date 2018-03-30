package com.javarnd.gj.CoreJavaLibrary.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
	
	private long id;
	private String name;
	private String userName;
	private String password;
	private List<Book> books = new ArrayList<>();
	private List<Book> referenceBooks = new ArrayList<>();

	
	public Student() {
		
	}
	
	public Student(long id, String name, String userName, String password) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.password = password;
	}
	
	public List<Book> getReferenceBooks() {
		return referenceBooks;
	}

	public void setReferenceBooks(List<Book> referenceBooks) {
		this.referenceBooks = referenceBooks;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
