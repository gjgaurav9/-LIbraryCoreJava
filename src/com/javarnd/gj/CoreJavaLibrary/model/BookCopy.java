package com.javarnd.gj.CoreJavaLibrary.model;

import java.util.Date;

import com.javarnd.gj.CoreJavaLibrary.enums.BookStatus;
import com.javarnd.gj.CoreJavaLibrary.enums.TypeOfBook;

public class BookCopy {
	
	private int bookCopyId;
	private String bookName;
	private Date issueDate;
	private Date lastDate;
	private BookStatus bookStatus;
	private String issedTo;
	private TypeOfBook typeOfBook;
	
	public BookCopy() {
		// TODO Auto-generated constructor stub
	}
	
	public BookCopy(int bookCopyId, TypeOfBook typeOfBook, int numberOfIssuableBook) {
		super();
		this.bookCopyId = bookCopyId;
		this.typeOfBook = typeOfBook;
		this.bookStatus = BookStatus.AVAILABLE;
	}

	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public int getBookCopyId() {
		return bookCopyId;
	}
	public void setBookCopyId(int bookCopyId) {
		this.bookCopyId = bookCopyId;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public BookStatus getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}
	public String getIssedTo() {
		return issedTo;
	}
	public void setIssedTo(String issedTo) {
		this.issedTo = issedTo;
	}
	public TypeOfBook getTypeOfBook() {
		return typeOfBook;
	}
	public void setTypeOfBook(TypeOfBook typeOfBook) {
		this.typeOfBook = typeOfBook;
	}
	
	

}
