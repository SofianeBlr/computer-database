package com.excilys.computerDatabase.dtos;

import com.excilys.computerDatabase.models.Computer;

public class ComputerDto {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDto company = new CompanyDto();
	public ComputerDto(Computer computer) {
		super();
		this.id = computer.getId().toString();
		this.name = computer.getName();
		if(computer.getIntroduced()!=null) {
			this.introduced = computer.getIntroduced().toString();
		}
		if(computer.getDiscontinued()!=null) {
			this.discontinued = computer.getDiscontinued().toString();
		}
		
		company = new CompanyDto(computer.getCompany());
	}
	public ComputerDto() {
	}
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
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public CompanyDto getCompany() {
		return company;
	}
	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	public void setCompanyId(String id) {
		this.company.setId(id);
	}
	public String getCompanyId() {
		return this.company.getId();
	}
	@Override
	public String toString() {
		return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + company + "]";
	}
	
}
