

import java.time.LocalDate;

import com.excilys.computerDatabase.dao.CompanyDao;
import com.excilys.computerDatabase.dao.ComputerDao;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;

public class TestDao {
	public static void main(String[] args) {
		CompanyDao companyDao = new CompanyDao();
		
		ComputerDao computerDao = new ComputerDao();
		
		
		/*
		Company test = new Company(0,"Lenovo2");
		test = companyDao.create(test);
		for(Company c : companyDao.getAll()) {
			System.out.printf("  %d  %50s%n",c.getId(),c.getName());
			
		}
		test.setName("Lenovo3");
		test = companyDao.update(test);
		Company c = companyDao.find(test.getId());
		System.out.printf("  %d  %50s%n",c.getId(),c.getName());
		companyDao.delete(test);*/
		
		Computer test = new Computer(0,"LENOVO",LocalDate.now());
		test = computerDao.create(test);
		for(Computer c : computerDao.getAll()) {
			String i = c.getIntroduced()!=null?c.getIntroduced().toString():"null";
			String d = c.getDiscontinued()!=null?c.getDiscontinued().toString():"null";
			System.out.printf("  %d  %70s  %20s%20s%5d%n",c.getId(),c.getName(),i,d,c.getCompanyId());
		}
		test.setName("LENOVO2");
		test = computerDao.update(test);
		for(Computer c : computerDao.getAll()) {
			String i = c.getIntroduced()!=null?c.getIntroduced().toString():"null";
			String d = c.getDiscontinued()!=null?c.getDiscontinued().toString():"null";
			System.out.printf("  %d  %70s  %20s%20s%5d%n",c.getId(),c.getName(),i,d,c.getCompanyId());
		}
		computerDao.delete(test);
		
	}
	
}
