package com.excilys.computerDatabase.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Company")
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name",nullable = true)
	private String name;
	
	private Company() {
	
	}

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
	@Override
	public String toString() {
		return String.format("  %d  %50s%n",id,name);
	}
	
	public static class CompanyBuilder {
		
		private Long idBuild;
		private String nameBuild;
		
		public CompanyBuilder setIdBuild(Long id) {
			this.idBuild = id;
			return this;
		}
		
		public CompanyBuilder setNameBuild(String name) {
			this.nameBuild = name;
			return this;
		}
		
		public Company build() {
			Company company = new Company();
			company.id = this.idBuild;
			company.name = this.nameBuild;
			
			return company;
		}
	}
	

}
