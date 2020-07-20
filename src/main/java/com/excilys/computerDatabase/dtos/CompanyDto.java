package com.excilys.computerDatabase.dtos;

import com.excilys.computerDatabase.models.Company;

public class CompanyDto {
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CompanyDto(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public CompanyDto(Company company) {
		if (company!=null)
		if(company.getId()!=null) {
			this.id = company.getId().toString();
			if(company.getName()!=null) 
			this.name= company.getName();
		}
	}
	public CompanyDto() {
	}
}
