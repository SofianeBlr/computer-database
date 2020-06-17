package com.excilys.computerDatabase.ui;

import java.util.List;
import java.util.Scanner;

public class Pages {
	static final int NUMBER_PER_PAGE = 10;
	
	
	
	static <T> void display(List<T> list, Scanner input) {
		int page=0;
		int length = list.size();
		int numberOfPage= (int) Math.ceil(length/(float)NUMBER_PER_PAGE);
		while(true) {
			for (int i = page*10; i < upperValue(length, page); i++) {
				System.out.print(list.get(i));
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
