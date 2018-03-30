package com.javarnd.gj.CoreJavaLibrary.library;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import com.javarnd.gj.CoreJavaLibrary.enums.UserType;
import com.javarnd.gj.CoreJavaLibrary.model.Book;
import com.javarnd.gj.CoreJavaLibrary.model.BookCopy;
import com.javarnd.gj.CoreJavaLibrary.model.Student;
import com.javarnd.gj.CoreJavaLibrary.scheduler.Scheduler;
import com.javarnd.gj.CoreJavaLibrary.service.BookService;
import com.javarnd.gj.CoreJavaLibrary.service.StudentService;

public class Library {

	private static BookService bookService = new BookService();
	private static StudentService studentService = new StudentService();
	private static boolean flagSearchDone = false;

	public static void main(String[] args) {
		welcomeToLibrary();
	}

	private static void welcomeToLibrary() {
		
		System.out.println("*********Welcome to Java RnD Library*********");
		System.out.println("***********Please Sign up/Sign in************");
		System.out.println();
		System.out.println("If you are new user: Enter 1 to Sign Up");
		System.out.println("If you are Existing user: Enter 2 to log in");
		
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int choice = reader.nextInt();

			if(choice == 1){
				addStudent(null,null);
				getStudents();
				decoration();
				System.out.println("Please, Continue Login");
				userLogin();
			}else if(choice == 2) {
				userLogin();
			}else {
				System.out.println("Wrong choice. PLease Enter again");
				welcomeToLibrary();
			}
		}catch (Exception e) {
			decoration();
			System.out.println("Wrong input! PLease try again");
			welcomeToLibrary();
		}


	}

	private static void userLogin() {
		decoration();
		try {
			System.out.println("Please Enter the username");
			Scanner userNameReader = new Scanner(System.in);  // Reading from System.in
			String userName = userNameReader.next();
			System.out.println("Please Enter the password");
			Scanner passwordReader = new Scanner(System.in);  // Reading from System.in
			String password = passwordReader.next();

			StudentService service = new StudentService();
			UserType typeOfUser = service.typeOfUser(userName, password);

			if(typeOfUser.equals(UserType.ADMIN)) {
				decoration();
				System.out.println("Welcome Admin");
				inventory(userName,typeOfUser);
				//adminlibrarySection(userName,typeOfUser);
			}else if(typeOfUser.equals(UserType.APPLICATIONUSER)) {
				decoration();
				System.out.println("Welcome " +userName);
				inventory(userName,typeOfUser);
				//userlibrarySection(userName, typeOfUser);
			}else if(typeOfUser.equals(UserType.INVALID)) {
				System.out.println("Wrong Id and Password. Please try login again");
				userLogin();
			}
		}catch (Exception e) {
			decoration();
			System.out.println("Wrong input! PLease try again");
			userLogin();
		}
	}

	private static void userlibrarySection(String userName, UserType userType) {

		System.out.println("Enter 1 for Reference only");
		System.out.println("Enter 2 for issuing a book");
		System.out.println("Enter 3 to view your profile");
		System.out.println("Enter 4 for Log out");

		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int choice = reader.nextInt();

			switch(choice){    
			case 1:    
				libraryForRefrenceBooks(userName, userType);
				break;  
			case 2:    
				libraryForIssuingBooks(userName, userType);
				break;
			case 3:    
				studentProfile(userName, userType);
				break;  
			case 4:    
				logout();
				break; 

			default: 
				System.out.println("Wrong choice. PLease Enter again");
				userlibrarySection(userName, userType);
			} 
		}catch (Exception e) {
			decoration();
			System.out.println("Wrong input! PLease try again");
			userlibrarySection(userName, userType);
		}

	}

	private static void studentProfile(String userName, UserType userType) {
		Student student = studentService.getUserProfile(userName);
		System.out.println();
		System.out.println("Welcome " +student.getName()+",");
		System.out.println("    Username : "+student.getUserName());
		System.out.println("    Password : "+student.getPassword());
		System.out.print("    List of books you have :");

		if(!student.getBooks().isEmpty()) {
			for(Book book :student.getBooks()) {
				System.out.println(book.getBookName());
			}
		}else {
			System.out.println("You dont have any book assigned on your name."
					+ "Please visit library to issue or refer a book.");
		}
	}

	private static void inventory(String userName, UserType userType) {
		
		decoration();
		System.out.println("We have the following books in the inventory.");
		List<Book> books=bookService.getBooks();
		listBooksForInventory(books);
		decoration();
		System.out.println("Enter 1 for searching a book");
		System.out.println("Enter 2 to perform other actions");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int choice = reader.nextInt();

			if(choice == 1){
				searchBook(userName, userType);
				flagSearchDone = true;
				roleBasedLibrary(userName, userType);
			}else if(choice == 2) {
				roleBasedLibrary(userName, userType);
			}else {
				System.out.println("Wrong choice. PLease Enter again");
				inventory(userName, userType);		
			}
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			inventory(userName, userType);
		}
	}

	private static void searchBook(String userName, UserType userType) {
		decoration();
		System.out.println("Enter 1 for searching on the basis of author name");
		System.out.println("Enter 2 for searching on the basis of book name");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int choice = reader.nextInt();

			if(choice == 1){
				searchBookByAuthorName(userName,  userType);
			}else if(choice == 2) {
				searchBookByBookName(userName,  userType);
			}else {
				System.out.println("Wrong choice. PLease Enter again");
				searchBook( userName,  userType);
			}
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			searchBook(userName, userType);
		}
	}

	private static void searchBookByBookName(String userName, UserType userType) {
		decoration();
		System.out.println("Please enter the book name");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			String bookName = reader.next();
			List<Book> bookWithSpecificAuthorName = bookService.searchBookByBookName(bookName);
			decoration();

			if(!bookWithSpecificAuthorName.isEmpty()) {
				System.out.println("We have following books containing '"+bookName+"'+ in the name");
				listBooksForInventory(bookWithSpecificAuthorName);
			}else { 
				System.out.println("Sorry !! We don't have any book of name "+bookName);
				System.out.println("PLease contact librarian for more information");
				continuation(userName, userType);
			}
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			searchBookByBookName(userName, userType);
		}


	}

	private static void searchBookByAuthorName(String userName, UserType userType) {
		decoration();
		System.out.println("Please enter the author name");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			String authorName = reader.next();
			List<Book> bookWithSpecificAuthorName = bookService.searchBookByAuthorName(authorName);	
			decoration();
			if(!bookWithSpecificAuthorName.isEmpty()) {
				System.out.println("We have following books containing '"+authorName+"'+ in the author name");
				listBooksForInventory(bookWithSpecificAuthorName);
			}else { 
				System.out.println("Sorry !! We don't have any book of "+authorName);
				System.out.println("PLease contact librarian for more information");
				continuation(userName, userType);
			}
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			searchBookByAuthorName(userName, userType);
		}

	}

	private static void adminlibrarySection(String userName, UserType userType) {

		System.out.println("Enter 1 for changing a book from refrence Book to Issuable");
		System.out.println("Enter 2 for adding a book");
		System.out.println("Enter 3 for adding a student");
		System.out.println("Enter 4 for deleting a book");
		System.out.println("Enter 5 for deleting a student");
		System.out.println("Enter 6 for AutoAssignment Books for students in queue");
		System.out.println("Enter 7 for Log out");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int choice = reader.nextInt();

			switch(choice){      
			case 1:    
				changeRefrenceToIssuing(userName, userType );
				break;  
			case 2:    
				addBook(userName, userType);
				break; 
			case 3:    
				addStudent(userName, userType);
				break;  
			case 4:    
				removeBook(userName, userType);
				break; 
			case 5:    
				removeStudent(userName, userType);
				break;
			case 6:    
				System.out.println("Auto assignment of books started: ");
				Timer timer = new Timer();
				timer.schedule(new Scheduler(), Date.from((Instant.now())));
				continuation(userName, userType);
				break;
			case 7:    
				logout();
				break; 

			default: 
				System.out.println("Wrong choice. PLease Enter again");
				adminlibrarySection(userName, userType);
			}
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			adminlibrarySection(userName, userType);
		}

	}

	private static void removeStudent(String userName, UserType userType) {
		System.out.println("List of student in library");
		List<Student> students =studentService.getStudents();
		getStudents();
		System.out.println("Please enter the username you want to remove");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			String username = reader.next();
			studentService.removeStudent(username);
			System.out.println("Student removed Successfully");
			continuation(userName, userType);
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			removeStudent(userName, userType);	
		}
		
	}

	private static void removeBook(String userName, UserType userType) {
		System.out.println("Available books in library");
		List<Book> books =bookService.getBooks();
		listBooksForInventory(books);
		System.out.println("Please enter the book id you want to remove");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int bookId = reader.nextInt();
			bookService.removeBook(bookId);
			System.out.println("Book removed Successfully");
			continuation(userName, userType);
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			removeBook(userName, userType);	
		}
		
	}

	private static void changeRefrenceToIssuing(String userName, UserType userType) {
		getAvailableBooks();
		try {
			System.out.println("choose the bookID to change from refrence book to Issuable book");
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int bookId = reader.nextInt();

			bookService.changeRefrenceToIssue(bookId);
			System.out.println();
			System.out.println("changed Succesfully");
			getAvailableBooks();
			continuation(userName, userType);
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			changeRefrenceToIssuing(userName, userType);
		}
	}

	private static void logout() {
		System.out.println("Successfully Logged Out");
		System.out.println();
		welcomeToLibrary();
	}

	private static void libraryForRefrenceBooks(String userName, UserType userType) {
		if(!flagSearchDone) {
			decoration();
			List<Book> books=bookService.getBooks();
			System.out.println("We have following refrence books in the Library:");
			listBooksForInventory(books);
		}
		readBook(userName, userType);
		flagSearchDone = false;
	}

	private static void readBook(String userName, UserType userType) {
		System.out.println("PLease enter the book id which you want to read");

		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			int bookId = reader.nextInt();

			Book book = bookService.getRefrenceBook(bookId, userName);
			decoration();
			System.out.println("Enjoy reading '" +book.getBookName() + "' by '"+ book.getAuthor()+"'");
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.MINUTE, 20);
			System.out.println("Please return the book with in 20 minuntes on or before "+ calendar.getTime());
			continuation(userName, userType);
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			readBook(userName, userType);
		}


	}

	private static void continuation(String userName, UserType userType) {
		System.out.println();
		System.out.println("***************************************************************");
		System.out.println("Enter 1 to log Out");
		System.out.println("Enter 2 to continue to the library");

		try {
			Scanner readerChoice = new Scanner(System.in);  // Reading from System.in
			int choice = readerChoice.nextInt();

			if(choice == 1) {
				logout();
			}else if(choice == 2) {
				inventory(userName, userType);
			}else {
				System.out.println("Wrong choice. PLease try again");
				inventory(userName, userType);
			}
		} catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			continuation(userName, userType);
		}

	}

	private static void roleBasedLibrary(String userName, UserType userType) {
		if(userType.equals(UserType.ADMIN)) {
			decoration();
			System.out.println("Welcome Admin");
			adminlibrarySection(userName, userType);
		}else if(userType.equals(UserType.APPLICATIONUSER)) {
			decoration();
			System.out.println("Welcome " +userName);
			userlibrarySection(userName, userType);
		}
	}

	private static void libraryForIssuingBooks(String userName, UserType userType) {

		if(!flagSearchDone) {
			List<Book> books=bookService.getBooks();
			decoration();
			System.out.println("We have following Books that can be issued from the Library:");
			listBooksForInventory(books);
		}
		issueBook(userName, userType);
	}

	private static void issueBook(String userName, UserType userType) {

		System.out.println("PLease enter the book id which you want to issue");
		try {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			long bookId = reader.nextLong();

			String result = bookService.issueBook(bookId, userName);

			if(result.equalsIgnoreCase("issued")) {
				decoration();
				System.out.println("Book issued Succesfully, Please check your name under issued to.");
				getAvailableBooks();
				Date currentDate = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DATE, 15);
				System.out.println();
				System.out.println("Please return the book within 15 days on or before "+ calendar.getTime());
			}else {
				decoration();
				System.out.println("Please check your name is there in the waiting list");
				getAvailableBooks();
			}
			flagSearchDone = false;
			continuation(userName, userType);
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			continuation(userName, userType);
		}
	}

	private static void addStudent(String user, UserType userType) {
		decoration();
		try {
			System.out.println("Please Enter the name");
			Scanner nameReader = new Scanner(System.in);  // Reading from System.in
			String name = nameReader.next();
			
			System.out.println("Please Enter the username");
			Scanner userNameReader = new Scanner(System.in);  // Reading from System.in
			String userName = userNameReader.next();
			System.out.println("Please Enter the password");
			Scanner passwordReader = new Scanner(System.in);  // Reading from System.in
			String password = passwordReader.next();

			Student student = new Student();

			student.setName(name);
			student.setUserName(userName);
			student.setPassword(password);
			String result = studentService.addStudent(student);
			if(result.equalsIgnoreCase("ADDED")) {
				System.out.println("Details has been Successfully. Please check the below list");
			}else if(result.equalsIgnoreCase("DUBLICATE")) {
				System.out.println("User already exist. Please try with another username");
				addStudent(user, userType);
			}else{
				System.out.println("Please try with another username");
				addStudent(user, userType);
			}
			if((UserType.ADMIN).equals(userType)) {
				continuation(user, userType);
			}

		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			addStudent(user, userType);
		}

	}

	private static void getStudents() {
		StudentService service = new StudentService();
		List<Student> students = service.getStudents();
		System.out.println("id "+ "Name "+" UserName");

		for(Student student:students) {
			System.out.println(" "+student.getId()+ "  "+student.getName()+ "  "
					+student.getUserName());
		}
	}

	private static void addBook(String userName, UserType userType) {

		try {
			System.out.println("Please Enter the Book name");
			Scanner bookNameReader = new Scanner(System.in);  // Reading from System.in
			String bookName = bookNameReader.next();
			System.out.println("Please Enter the author name");
			Scanner authorReader = new Scanner(System.in);  // Reading from System.in
			String author = authorReader.next();
			System.out.println("Please Enter the number of books");
			Scanner numberOfBooksReader = new Scanner(System.in);  // Reading from System.in
			int numberOfBooks = numberOfBooksReader.nextInt();

			bookService.getBooks();
			Book book  = new Book();
			book.setAuthor(author);
			book.setBookName(bookName);
			book.setNumberOfBooks(numberOfBooks);

			bookService.addNewBook(book);
			decoration();
			System.out.println("Book added Successfully");
			List<Book> books=bookService.getBooks();
			listBooksForInventory(books);
			continuation(userName, userType);
		}catch (Exception e) {
			System.out.println("Wrong input! PLease try again");
			addBook(userName, userType);
		}

	}

	private static void decoration() {
		System.out.println();
		System.out.println("***************************************************************");
		System.out.println("***************************************************************");

	}

	private static void getAvailableBooks() {
		List<Book> books=bookService.getBooks();
		printBooks(books);
	}

	private static void listBooksForInventory(List<Book> books) {

		System.out.println("BookId" + "   "+ "BookName" + "   "+ "AuthorName"+ "   "+"Number of Books Available to issue");
		for(Book book:books) {
			List<BookCopy> bookCopies = book.getMapBookAndCopies().get(book.getBookId());
			System.out.println("   "+book.getBookId()+ "       "+book.getBookName()+ "         "+book.getAuthor()+ "         "+book.getNumberOfIssuableBook()+ "         ");
		}
	}

	private static void printBooks(List<Book> books) {

		System.out.println("BookId" + "   "+ "BookName" + "   "+ "AuthorName"+ "   "+" Number of Books"+" "+"BookCopyId"
				+ "     "+"BookStatus" + "     "+"TypeOfBook" + "         "+"IssueDate"
				+ "         "+"LastDate" + "         "+"IssedTo" + "         "+ "StudentsInQueue");
		for(Book book:books) {
			List<BookCopy> bookCopies = book.getMapBookAndCopies().get(book.getBookId());
			System.out.print("   "+book.getBookId()+ "       "+book.getBookName()+ "         "+book.getAuthor()+ "         "+book.getNumberOfBooks()+ "         ");

			for(int i=0; i<bookCopies.size(); i++) {
				if(i != 0) {
					System.out.print("                                                    ");
				}
				if(bookCopies.get(i).getBookCopyId()!=0) {
					System.out.print(bookCopies.get(i).getBookCopyId()+".    ");
				}
				System.out.print(bookCopies.get(i).getBookStatus()+ "          "+bookCopies.get(i).getTypeOfBook()+ "          "
						+bookCopies.get(i).getIssueDate()+ "          "+bookCopies.get(i).getLastDate()+ "          "
						+bookCopies.get(i).getIssedTo()+ "          ");
				if(i+1<bookCopies.size()) {
					System.out.println();
				}
			}

			List<Student> students = book.getStudentsInQueueforIssuingBook();
			for(int i=0; i<students.size(); i++) {
				System.out.print(students.get(i).getName());
				if(i+1<students.size()) {
					System.out.print(",");
				}

			}

			System.out.println();
		}
	}
}
