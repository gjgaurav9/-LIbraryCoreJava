package com.javarnd.gj.CoreJavaLibrary.scheduler;

import java.util.Date;
import java.util.TimerTask;

import com.javarnd.gj.CoreJavaLibrary.service.BookService;

public class Scheduler extends TimerTask{
	
	BookService bookService = new BookService();
    public Scheduler() {
        
    }
    public void run() {
        bookService.autoAssignmentBookIssue();
    }	
}
