package com.excilys.computerDatabase.ui;

import java.util.List;
import java.util.Scanner;

import com.excilys.computerDatabase.service.ServiceDao;


public class PageDisplay {
	public static final int NUMBER_PER_PAGE = 10;
	
	
	
	static <T> void display(ServiceDao<T> pageService, Scanner input) {
		int page=0;
		int numberOfPage= pageService.getMaxPage();
		List<T> list;
		while(true) {
			list = pageService.getPage(page);
			for (T l : list) {
				System.out.print(l);
			}
			int choice = pageNav(page, numberOfPage, input);
			if (choice == 0) page--;
			if (choice == 1) page++;
			if(choice ==2) return;
			if(choice ==3) page = numberOfPage-1;
			page %= numberOfPage;
			
		}
		
	}
	
	static int upperValue(int length, int page) {
		int upperV ;
		
		if(page>0) upperV=((page+1) * NUMBER_PER_PAGE);
		
		else upperV = 10;
		
		return upperV>length? length: upperV;
	}
	
	static int pageNav(int page,int numbOfPage, Scanner input) {
		System.out.println("page:"+(page+1)+"/"+numbOfPage);
		while(true) {
			if(page>0 && page+1<numbOfPage) {
				System.out.print("Enter < or > to navigate pages (enter exit to leave):");
			}
			else if(page>0) {
				System.out.print("Enter < to go to previous page (enter exit to leave):");
			}
			else if(page+1<numbOfPage){
				System.out.print("Enter > to go to next page (enter exit to leave):");
			}
			else {
				System.out.print(" (enter exit to leave):");
			}
			String inputString = input.next();
			
			if (inputString.equals("<") && page>0) return 0;
			if (inputString.equals(">")) return 1;
			if (inputString.equals("exit")) return 2;
			if (inputString.equals("<") && page==0) return 3;
			
		}
	}
}
