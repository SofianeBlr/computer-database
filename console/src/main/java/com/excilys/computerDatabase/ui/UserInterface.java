package com.excilys.computerDatabase.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.services.CompanyService;
import com.excilys.computerDatabase.services.ComputerService;
import com.excilys.computerDatabase.springConfiguration.CliConfiguration;

public class UserInterface {
	static Scanner input =new Scanner(System.in);
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	
	static CompanyService companyService;
	
	
	static ComputerService computerService;

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(CliConfiguration.class);
		companyService =  ctx.getBean(CompanyService.class);
		computerService =  ctx.getBean(ComputerService.class);
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
		System.out.printf("%n%n%20s Computer/Company application %n","");
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
		PageDisplay.display(computerService, input);
	}
	public static void displayAllCompany() {
		PageDisplay.display(companyService, input);
	}
	
	public static void createComputer() {
		System.out.println("  -Create a computer:");
		String name = getString("computer name");
		LocalDate introduced = getDate("introduced");
		LocalDate discontinued = getDate("discontinued");
		Long companyId = getLong("company id");
		Computer comp = new Computer(0L,name,introduced,discontinued,companyId);
		comp = computerService.create(comp);
		displayComputer(comp);
	}
	
	public static void updateComputer() {
		Computer comp = null;
		Long computerId=null;
		System.out.println("  -Update a computer:");
		
		while(comp==null) {
			computerId = getLong("the id of the computer to update");
			comp = computerService.find(computerId);
			if(comp== null) {
				System.out.println("wrong id : Unable to find");
			}
			else {
				System.out.println(comp);
			}
		}
		
		String name = getString("new computer name");
		LocalDate introduced = getDate("new introduced");
		LocalDate discontinued = getDate("new discontinued");
		Long companyId = getLong("new company id");
		comp = new Computer(computerId,name,introduced,discontinued,companyId);
		comp = computerService.update(comp);
		System.out.println("updated computer : ");
		displayComputer(comp);
	}
	
	public static void deleteComputer() {
		System.out.println("  -Delete a computer:");
		Long computerId = getLong("the id of the computer to delete");
		Computer comp = computerService.find(computerId);
		if(comp!= null) {
			computerService.delete(comp.getId());
			displayComputer(comp);
		}
		else {
			System.out.println("id not found");
		}
		
	}
	public static void getComputer() {
		System.out.println("  -Show a computer:");
		Long computerId = getLong("the id of the computer to show");
		Computer comp = new Computer(computerId);
		comp = computerService.find(computerId);
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
				input.next();
			}
		}
		
		return number;
	}
	
	public static Long getLong(String valueName) {
		boolean run = true;
		Long number=null;
		while(run) {
			run = false;
			System.out.printf("%n enter "+ valueName +" : ");
			try {
				number =input.nextLong();
			}catch(InputMismatchException e){
				System.out.println("Invalid input : input must be a number");
				run = true;
				input.next();
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
