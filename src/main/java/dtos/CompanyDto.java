package dtos;

import com.excilys.computerDatabase.models.Company;

public class CompanyDto {
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CompanyDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public CompanyDto(Company company) {
		this.id = company.getId();
		this.name= company.getName();
	}
}
