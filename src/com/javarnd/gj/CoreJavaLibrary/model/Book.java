package com.javarnd.gj.CoreJavaLibrary.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javarnd.gj.CoreJavaLibrary.enums.BookStatus;
import com.javarnd.gj.CoreJavaLibrary.enums.TypeOfBook;

public class Book {
	
	private long bookId;
	private String bookName;
	private String author;
	private int numberOfBooks;
	private int numberOfIssuableBook;
	private Map<Long, List<BookCopy>> mapBookAndCopies = new HashMap<Long, List<BookCopy>>();
	private List<Student> studentsInQueueforIssuingBook = new ArrayList<>();
	private List<Student> studentsInQueueforReferingBook = new ArrayList<>();

	
	public Book() {
		
	}

	public Book(long bookId, String bookName, String author, int numberOfBooks) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.numberOfBooks = numberOfBooks;
		
		ArrayList<BookCopy> bookCopies = new ArrayList<BookCopy>();
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBookCopyId(1);
		//First book is by default Reference type and rest will be available as issuable books.
		bookCopy.setTypeOfBook(TypeOfBook.REFRENCEBOOK);
		bookCopy.setBookStatus(BookStatus.AVAILABLE);
		bookCopies.add(bookCopy);
		this.numberOfIssuableBook = numberOfBooks-1;
		this.mapBookAndCopies.put(bookId, bookCopies);
	}
	

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<Student> getStudentsInQueueforIssuingBook() {
		return studentsInQueueforIssuingBook;
	}

	public void setStudentsInQueueforIssuingBook(List<Student> studentsInQueueforIssuingBook) {
		this.studentsInQueueforIssuingBook = studentsInQueueforIssuingBook;
	}

	public int getNumberOfBooks() {
		return numberOfBooks;
	}

	public void setNumberOfBooks(int numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}

	public Map<Long, List<BookCopy>> getMapBookAndCopies() {
		return mapBookAndCopies;
	}

	public void setMapBookAndCopies(Map<Long, List<BookCopy>> mapBookAndCopies) {
		this.mapBookAndCopies = mapBookAndCopies;
	}

	public int getNumberOfIssuableBook() {
		return numberOfIssuableBook;
	}

	public void setNumberOfIssuableBook(int numberOfIssuableBook) {
		this.numberOfIssuableBook = numberOfIssuableBook;
	}

	public List<Student> getStudentsInQueueforReferingBook() {
		return studentsInQueueforReferingBook;
	}

	public void setStudentsInQueueforReferingBook(List<Student> studentsInQueueforReferingBook) {
		this.studentsInQueueforReferingBook = studentsInQueueforReferingBook;
	}
}
