package com.excilys.computerDatabase.servlets;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DashboardTest {
	WebDriver driver;
	 
	
	@Before
	 public void setup() {
		 driver = new FirefoxDriver();		 
	 }
	@After
	public void quit() {
		driver.quit();
	}
	
	@Test
	public void TestNbOfComputer() {
		driver.get("http://localhost:8080/ComputerDb");
		String text = driver.findElement(By.id("homeTitle")).getText();
		assertEquals("500", text.split(" ")[0]);
	}
	
	

}
