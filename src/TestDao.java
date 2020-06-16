

import java.time.LocalDate;

import com.excilys.computerDatabase.Dao.CompanyDao;
import com.excilys.computerDatabase.Dao.ComputerDao;
import com.excilys.computerDatabase.Model.Company;
import com.excilys.computerDatabase.Model.Computer;

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
		
		Computer test = new Computer(0,"LENOVO",LocalDate.now(),LocalDate.now(),14);
		test = computerDao.create(test);
		for(Computer c : computerDao.getAll()) {
			String i = c.getIntroduced()!=null?c.getIntroduced().toString():"null";
			String d = c.getIntroduced()!=null?c.getIntroduced().toString():"null";
			System.out.printf("  %d  %70s  %20s%20s%5d%n",c.getId(),c.getName(),i,d,c.getCompanyId());
		}
		test.setName("LENOVO2");
		test = computerDao.update(test);
		System.out.printf("  %d  %50s%n",test.getId(),test.getName());
		computerDao.delete(test);
		
	}
	
}
