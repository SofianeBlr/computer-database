package com.excilys.computerDatabase.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.computerDatabase.dao.CompanyDao;
import com.excilys.computerDatabase.dao.ComputerDao;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;

public class UserInterface {
	static Scanner input =new Scanner(System.in);
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	static ComputerDao computerDao = new ComputerDao();
	static CompanyDao companyDao = new CompanyDao();

	public static void main(String[] args) {
		while(true) {
			int d =mainMenu();
			switch (d) {
			case 1:
				displayAllComputer();
				if (exit()) return;
				break;
			case 2:
				displayAllCompany();
				if (exit()) return;
				break;
			case 3:
				getComputer();
				if (exit()) return;
				break;
			case 4:
				createComputer();
				if (exit()) return;
				break;
			case 5:
				updateComputer();
				if (exit()) return;
				break;
			case 6:
				deleteComputer();
				if (exit()) return;
				break;
			}

			
		}
		
	}
	
	public static int mainMenu() {
		int option = 0;
		System.out.printf("%20s Computer/Company application %n","");
		System.out.printf("%20s          Main Menu %n","");
		System.out.printf("%n%n%n%10s -Press 1 to list all computer %n","");
		System.out.printf("%10s -Press 2 to list all company %n","");
		System.out.printf("%10s -Press 3 to show computer details %n","");
		System.out.printf("%10s -Press 4 to create a computer %n","");
		System.out.printf("%10s -Press 5 to update a computer %n","");
		System.out.printf("%10s -Press 6 to delete a computer %n","");
        option =getInt("");
		return option;
	}
	
	public static void displayAllComputer() {
		Pages.display(computerDao.getAll(), input);
	}
	public static void displayAllCompany() {
		Pages.display(companyDao.getAll(), input);
	}
	
	public static void createComputer() {
		System.out.println("  -Create a computer:");
		String name = getString("computer name");
		LocalDate introduced = getDate("introduced");
		LocalDate discontinued = getDate("discontinued");
		int companyId = getInt("company id");
		Computer comp = new Computer(0,name,introduced,discontinued,companyId);
		comp = computerDao.create(comp);
		displayComputer(comp);
	}
	
	public static void updateComputer() {
		System.out.println("  -Update a computer:");
		int computerId = getInt("the id of the computer to update");
		String name = getString("new computer name");
		LocalDate introduced = getDate("new introduced");
		LocalDate discontinued = getDate("new discontinued");
		int companyId = getInt("new company id");
		Computer comp = new Computer(computerId,name,introduced,discontinued,companyId);
		comp = computerDao.update(comp);
		displayComputer(comp);
	}
	
	public static void deleteComputer() {
		System.out.println("  -Delete a computer:");
		int computerId = getInt("the id of the computer to delete");
		Computer comp = computerDao.find(computerId);
		if(comp!= null) {
			computerDao.delete(comp);
			displayComputer(comp);
		}
		else {
			System.out.println("id not found");
		}
		
	}
	public static void getComputer() {
		System.out.println("  -Show a computer:");
		int computerId = getInt("the id of the computer to show");
		Computer comp = new Computer(computerId);
		comp = computerDao.find(computerId);
		displayComputer(comp);
	}
	
	public static boolean exit() {
		String response = "";
		System.out.printf(" Go back to main menu y/n? ");
		response =input.next();
        boolean option =response.equals("n");
		return option;
	}
	
	public static LocalDate getDate(String valueName) {
		LocalDate localdate = null;
		while(true) {
			System.out.printf("%n enter "+ valueName +" date dd/mm/yyyy (enter n for null) : ");
			String date =input.next();
			if(!date.equals("n")) {
				try {
					localdate =LocalDate.parse(date,formatter); 
					return localdate;
				}catch(DateTimeParseException e){
					System.out.println("Invalid input : date cannot be parsed");
				}
			}
			else return null;
		}
		
		
	}
	
	public static int getInt(String valueName) {
		boolean run = true;
		int number = 0;
		while(run) {
			run = false;
			System.out.printf("%n enter "+ valueName +" : ");
			try {
				number =input.nextInt();
			}catch(InputMismatchException e){
				System.out.println("Invalid input : input must be a number");
				run = true;
			}
		}
		
		return number;
	}
	
	public static String getString(String valueName) {
		boolean run = true;
		String name = "";
		while(run) {
			run = false;
			System.out.printf("%n enter "+ valueName +" : ");
			name =input.next();
			if(name.isEmpty()) {
				System.out.println("Invalid input : empty name");
				run = true;
			}
		}
		return name;
	}
	
	public static void displayComputer(Computer c) {
		if (c == null) {
			System.out.println("computer not found");
			return;
		}
		System.out.print(c);
	}
	
	public static void displayCompany(Company c) {
		if (c == null) {
			System.out.println("company not found");
			return;
		}
		System.out.print(c);
	}
}
