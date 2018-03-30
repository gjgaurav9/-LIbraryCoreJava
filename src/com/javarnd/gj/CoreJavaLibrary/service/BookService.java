package com.javarnd.gj.CoreJavaLibrary.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.javarnd.gj.CoreJavaLibrary.dummyDatabase.Database;
import com.javarnd.gj.CoreJavaLibrary.enums.BookStatus;
import com.javarnd.gj.CoreJavaLibrary.enums.TypeOfBook;
import com.javarnd.gj.CoreJavaLibrary.model.Book;
import com.javarnd.gj.CoreJavaLibrary.model.BookCopy;
import com.javarnd.gj.CoreJavaLibrary.model.Student;

public class BookService {
	
	private Map<Long, Book> booksMap = Database.getBooks();
	private Map<String , Student> studentsMap = Database.getStudents();

	
	public BookService() {
		booksMap.put(1l, new Book(1l, "java", "gosling",2 ));
		booksMap.put(2l, new Book(2l, "species", "Darwin" ,3));
		booksMap.put(3l, new Book(3l, "Walden", "Henry David" ,3));
		booksMap.put(4l, new Book(4l, "Two Lives", "Vikram Seth" ,2));
	}

	public List<Book> getBooks() {
		ArrayList<Book> books = new ArrayList<>(booksMap.values());
		return books;
	}
	
	public void addNewBook(Book book) {
		ArrayList<Book> books = new ArrayList<>(booksMap.values());
		book.setBookId(books.size()+1);
	    ArrayList<BookCopy> bookCopies = new ArrayList<BookCopy>();
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBookCopyId(1);
		bookCopy.setTypeOfBook(TypeOfBook.REFRENCEBOOK);
		bookCopy.setBookStatus(BookStatus.AVAILABLE);
		bookCopies.add(bookCopy);
		book.setNumberOfIssuableBook(book.getNumberOfBooks()-1);
		book.getMapBookAndCopies().put(book.getBookId(), bookCopies);
		booksMap.put(book.getBookId(), book);
	}
	
	
	public Book getRefrenceBook(long id, String userName) {
	
		Book book = booksMap.get(id);
		Map<Long, List<BookCopy>> mapBookCopy = book.getMapBookAndCopies();
		ArrayList<BookCopy> bookCopies = new ArrayList<>(mapBookCopy.get(id));
		Student student = studentsMap.get(userName);
		
		BookCopy bookCopy = bookCopies.get(0);
		if(bookCopy.getIssedTo() == null) {
			bookCopy.setBookCopyId(bookCopies.size());
			addBookCopyForReference(userName, bookCopies, bookCopy);
			student.getBooks().add(book);
			return book;
		}else {
			System.out.println();
			System.out.println("Sorry ! We cannot issue this book right now");
			System.out.println("But your name has been added in the queue");
			System.out.println("You can get at "+bookCopy.getLastDate());
			
			book.getStudentsInQueueforReferingBook().add(student);
			return null;
		}
	
	}
	
	private void addBookCopyForReference(String userName, ArrayList<BookCopy> bookCopies,  BookCopy bookCopy) {
		Date currentDate = new Date();
		bookCopy.setIssedTo(userName);	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MINUTE, 30);
		bookCopy.setIssueDate(currentDate);
		bookCopy.setLastDate(calendar.getTime());
		bookCopy.setBookStatus(BookStatus.TAKENFORREFRENCE);
	}	
	
	public String issueBook(long id, String userName) {
		
		Book book = booksMap.get(id);
		
		Map<Long, List<BookCopy>> mapBookCopy = book.getMapBookAndCopies();
		ArrayList<BookCopy> bookCopies = new ArrayList<>(mapBookCopy.get(id));
		
		Student student = studentsMap.get(userName);
		
		if(bookCopies.size()<book.getNumberOfBooks()) {
			for(BookCopy bookCopy:bookCopies) {
				
				if(bookCopy.getIssedTo() == null && bookCopy.getBookCopyId() != 1) {
					bookCopy.setBookCopyId(bookCopies.size());
					addBookCopy(userName, bookCopies, bookCopy);
					student.getBooks().add(book);
				}else {
					
					bookCopy = new BookCopy();
					bookCopy.setBookCopyId(bookCopies.size()+1);
					addBookCopy(userName, bookCopies, bookCopy);
					mapBookCopy.get(id).add(bookCopy);
					book.setNumberOfIssuableBook(book.getNumberOfIssuableBook()-1);
				}
				break;
			}
			booksMap.put(id, book);
			return "issued";
		}else {
			System.out.println();
			System.out.println("Sorry ! We cannot issue this book right now");
			System.out.println("But your name has been added in the queue");
			System.out.println("It will be automatically issued to you on availability");
			book.getStudentsInQueueforIssuingBook().add(student);
			return "queued";
		}
	}


	private void addBookCopy(String userName, ArrayList<BookCopy> bookCopies , BookCopy bookCopy) {
		
		Date currentDate = new Date();
		bookCopy.setIssedTo(userName);	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, 15);
		bookCopy.setIssueDate(currentDate);
		bookCopy.setLastDate(calendar.getTime());
		bookCopy.setTypeOfBook(TypeOfBook.ISSUEABLEBOOK);
		bookCopy.setBookStatus(BookStatus.ISSUED);
	}	
	
	
	public void changeRefrenceToIssue(long id) {
		Book book = booksMap.get(id);
		Map<Long, List<BookCopy>> mapBookCopy = book.getMapBookAndCopies();
		ArrayList<BookCopy> bookCopies = new ArrayList<BookCopy>(mapBookCopy.get(id));
		BookCopy bookCopy = bookCopies.get(0);
		bookCopy.setTypeOfBook(TypeOfBook.ISSUEABLEBOOK);
		booksMap.put(id, book);
	}
	
	public List<Book> searchBookByAuthorName(String authorName) {
		ArrayList<Book> books = new ArrayList<>(booksMap.values());
		ArrayList<Book> bookWithSpecificAuthorName = new ArrayList<>();
		for(Book book: books) {
			if(book.getAuthor().contains(authorName)) {
				bookWithSpecificAuthorName.add(book);
			}
		}
		return bookWithSpecificAuthorName;
	}
	
	public List<Book> searchBookByBookName(String bookName) {
		ArrayList<Book> books = new ArrayList<>(booksMap.values());
		ArrayList<Book> bookWithSpecificBookName = new ArrayList<>();
		for(Book book: books) {
			if(book.getBookName().contains(bookName)) {
				bookWithSpecificBookName.add(book);
			}
		}
		return bookWithSpecificBookName;
	}
	
	public void removeBook(long bookId) {
		booksMap.remove(bookId);
	}
	
	public void autoAssignmentBookIssue() {
		ArrayList<Book> books = new ArrayList<>(booksMap.values());
		for(Book book: books) {
			List<BookCopy> bookCopies = new ArrayList<>(book.getMapBookAndCopies().get(book.getBookId()));
			for(BookCopy bookCopy : bookCopies) {
				List<Student> students = book.getStudentsInQueueforIssuingBook();
				if(bookCopy.getLastDate() != null){
					if((new Date()).after(bookCopy.getLastDate())){
						if(!students.isEmpty()) {
							Student student = students.get(0);
							issueBook(book.getBookId(), student.getUserName());
							students.remove(student);
							System.out.println("Book issued to : "+student.getUserName());
						}
					}
				} 
			}
		}
	}
}
