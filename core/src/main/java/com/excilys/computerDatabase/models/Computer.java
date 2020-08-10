package com.excilys.computerDatabase.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")

	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "introduced")
	private LocalDate introduced = null;

	@Column(name = "discontinued")
	private LocalDate discontinued = null;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	private Company company = new Company.CompanyBuilder().build();

	
	private Computer() {
		
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) throws IllegalArgumentException {
		if (introduced == null || this.discontinued == null) {
			this.introduced = introduced;
		} else if (introduced.isBefore(discontinued) || introduced.equals(discontinued)) {
			this.introduced = introduced;
		} else {
			throw new IllegalArgumentException("discontinued date < introduced date");
		}
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) throws IllegalArgumentException {
		if (this.introduced == null || discontinued == null) {
			this.discontinued = discontinued;
		} else if (this.introduced.isBefore(discontinued) || this.introduced.isEqual(discontinued)) {
			this.discontinued = discontinued;
		} else {
			throw new IllegalArgumentException("discontinued date < introduced date");
		}
	}

	public Long getCompanyId() {
		if (company != null) {
			return company.getId();
		} else {
			return null;
		}
	}

	public void setCompanyId(Long companyId) {
		this.company.setId(companyId);
	}

	public String getCompanyName() {
		return company.getName();
	}

	public void setCompanyName(String name) {
		this.company.setName(name);
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company c) {
		this.company = c;
	}

	@Override
	public String toString() {
		String i = getIntroduced() != null ? getIntroduced().toString() : "null";
		String d = getDiscontinued() != null ? getDiscontinued().toString() : "null";
		String companyId = getCompanyId() != null ? getCompanyId().toString() : "null";
		return String.format("  %d  %40s  %20s%20s%5s%n", getId(), getName(), i, d, companyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Computer other = (Computer) obj;
		if (this.getCompanyId() != other.getCompanyId()) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}



	public static class ComputerBuilder {

		private Long idBuild;
		private String nameBuild;
		private LocalDate introducedBuild;
		private LocalDate discontinuedBuild;
		private Company companyBuild;
		
		public ComputerBuilder() {
			
		}

		public ComputerBuilder setIdBuild(Long id) {
			this.idBuild = id;
			return this;
		}

		public ComputerBuilder setNameBuild(String name) {
			this.nameBuild = name;
			return this;
		}

		public ComputerBuilder setIntroducedBuild(LocalDate introduced) {
			this.introducedBuild = introduced;
			return this;
		}

		public ComputerBuilder setDiscontinuedBuild(LocalDate dicontinued) {
			this.discontinuedBuild = dicontinued;
			return this;
		}

		public ComputerBuilder setCompanyBuild(Company company) {
			this.companyBuild = company;
			return this;
		}

		public Computer build() {
			Computer computer = new Computer();
			computer.name = this.nameBuild;
			computer.id = this.idBuild;
			
			computer.introduced = this.introducedBuild;
			computer.discontinued = this.discontinuedBuild;
			computer.company = this.companyBuild;
			
			return computer;
			
		}

	}

}
