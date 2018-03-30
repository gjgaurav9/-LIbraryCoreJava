package com.javarnd.gj.CoreJavaLibrary.dummyDatabase;

import java.util.HashMap;
import java.util.Map;

import com.javarnd.gj.CoreJavaLibrary.model.Book;
import com.javarnd.gj.CoreJavaLibrary.model.Student;

public class Database {
	
		private static Map<String, Student> students = new HashMap<>();
		private static Map<Long , Book> books = new HashMap<>();
		
		public static Map<String, Student> getStudents() {
			return students;
		}
		public static Map<Long, Book> getBooks() {
			return books;
		}
}
