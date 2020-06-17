package com.excilys.computerDatabase.model;

import java.time.LocalDate;

public class Computer {
	private int id;
	private String name;
	private LocalDate introduced= null;
	private LocalDate discontinued = null;
	private int companyId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, int companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}
	public Computer(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Computer(int id, String name, LocalDate introduced, int companyId) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.companyId = companyId;
	}
	public Computer(int id, String name, LocalDate introduced) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
	}
	public Computer(int id, String name, int companyId) {
		this.id = id;
		this.name = name;
		this.companyId = companyId;
	}
	public Computer(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		String i = getIntroduced()!=null?getIntroduced().toString():"null";
		String d = getDiscontinued()!=null?getDiscontinued().toString():"null";
		return String.format("  %d  %70s  %20s%20s%5d%n",getId(),getName(),i,d,getCompanyId());
	}

}
