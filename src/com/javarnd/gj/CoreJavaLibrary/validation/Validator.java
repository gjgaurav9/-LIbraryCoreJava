package com.javarnd.gj.CoreJavaLibrary.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	/*Pattern pattern = Pattern.compile("name");
	Matcher matcher = pattern.matcher("");*/
	
	
	
	public static void main(String[] args) {
		String name = "  ";
		String regex= "\\s";
		
		System.out.println(name.matches(regex));
	}
}
