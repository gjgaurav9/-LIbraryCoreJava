package com.javarnd.gj.CoreJavaLibrary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javarnd.gj.CoreJavaLibrary.dummyDatabase.Database;
import com.javarnd.gj.CoreJavaLibrary.enums.UserType;
import com.javarnd.gj.CoreJavaLibrary.model.Student;

public class StudentService {
	
	private Map<String , Student> studentsMap = Database.getStudents();
	
	public StudentService() {
		studentsMap.put("gjgaurav", new Student(1l, "Gaurav","gjgaurav", "password"));
		studentsMap.put("sumitc", new Student(2l, "sumit", "sumitc", "choksey"));
		studentsMap.put("ashokn", new Student(3l, "ashok", "ashokn", "nair"));
		studentsMap.put("ankitj", new Student(4l, "ankit", "ankitj", "jain"));
		studentsMap.put("admin", new Student(5l, "admin", "admin", "admin"));

	}
	
	public String addStudent(Student student) {
		ArrayList<Student> studentsList = new ArrayList<Student>(studentsMap.values());
		String userName = student.getUserName();
		for(Student studentExisting:studentsList) {
			if(!userName.equalsIgnoreCase(studentExisting.getUserName())) {
				student.setId(studentsList.size()+1);
				studentsMap.put(student.getUserName(), student);
				return "ADDED";
			}else {
				return "DUBLICATE";
			}
		}
		return null;
		
	}
	
	public List<Student> getStudents(){
		ArrayList<Student> studentsList = new ArrayList<Student>(studentsMap.values());
		return studentsList;
				
	}
	
	public String checkUser(String userName, String password) {
		ArrayList<Student> students = new ArrayList<Student>(studentsMap.values());
		for(Student student : students) {
			if(userName.equalsIgnoreCase(student.getUserName()) && 
				password.equalsIgnoreCase(student.getPassword())) {
				return userName;
			}
		}
		return null;
	}
	
	public UserType typeOfUser(String userName, String password) {
		String user =checkUser(userName, password);
		if("admin".equalsIgnoreCase(user)) {
			return UserType.ADMIN;
		}else if(null != user){
			return UserType.APPLICATIONUSER;
		}else {
			return UserType.INVALID;
		}
	}
	
	public Student getUserProfile(String userName) {
		ArrayList<Student> students = new ArrayList<Student>(studentsMap.values());
		for(Student student : students) {
			if(userName.equalsIgnoreCase(student.getUserName())) {
				return student;
			}
		}
		return null;
	}
	
	public void removeStudent(String username) {
		studentsMap.remove(username);
	}
}
