package com.excilys.computerDatabase.models;

import java.time.LocalDate;

public class Computer {
	private Long id;
	private String name;
	private LocalDate introduced= null;
	private LocalDate discontinued = null;
	private Company company = new Company();
	
	
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
	public void setIntroduced(LocalDate introduced) throws IllegalArgumentException{
		if(introduced == null || this.discontinued == null) {
			this.introduced = introduced;
		}
		else if(this.introduced.isBefore(discontinued)) {
			this.introduced = introduced;
		}
		else {
			throw new IllegalArgumentException("discontinued date < introduced date");
		}
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) throws IllegalArgumentException {
		if(this.introduced == null || discontinued==null) {
			this.discontinued = discontinued;
		}
		else if(this.introduced.isBefore(discontinued)) {
			this.discontinued = discontinued;
		}
		else {
			throw new IllegalArgumentException("discontinued date < introduced date");
		}

	}
	public Long getCompanyId() {
		return company.getId();
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
	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) throws IllegalArgumentException {
		this.id = id;
		this.name = name;
		this.company.setId(companyId);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
	}
	public Computer(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Computer(Long id, String name, LocalDate introduced, Long companyId) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.company.setId(companyId);
	}
	public Computer(Long id, String name, LocalDate introduced) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
	}
	public Computer(Long id, String name, Long companyId) {
		super();
		this.id = id;
		this.name = name;
		this.company.setId(companyId);
	}
	public Computer(Long id) {
		super();
		this.id = id;
	}
	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued)throws IllegalArgumentException {
		super();
		this.id = id;
		this.name = name;
		setIntroduced(introduced);
		setDiscontinued(discontinued);
	}
	public Computer() {
	}
	@Override
	public String toString() {
		String i = getIntroduced()!=null?getIntroduced().toString():"null";
		String d = getDiscontinued()!=null?getDiscontinued().toString():"null";
		String companyId = getCompanyId()!=null?getCompanyId().toString():"null";
		return String.format("  %d  %40s  %20s%20s%5s%n",getId(),getName(),i,d,companyId);
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
		if (this.getCompanyId()!=other.getCompanyId()) {
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

}
