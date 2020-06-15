
import com.excilys.computerDatabase.Dao.CompanyDao;
import com.excilys.computerDatabase.Dao.ComputerDao;
import com.excilys.computerDatabase.Model.Company;
import com.excilys.computerDatabase.Model.Computer;

public class TestDao {
	public static void main(String[] args) {
		CompanyDao companyDao = new CompanyDao();
		
		for(Company c : companyDao.getAll()) {
			System.out.printf("  %d  %50s%n",c.getId(),c.getName());
			
		}
		
		ComputerDao computerDao = new ComputerDao();
		
		for(Computer c : computerDao.getAll()) {
			String i = c.getIntroduced()!=null?c.getIntroduced().toString():"null";
			String d = c.getIntroduced()!=null?c.getIntroduced().toString():"null";
			System.out.printf("  %d  %70s  %20s%20s%5d%n",c.getId(),c.getName(),i,d,c.getCompanyId());
			
		}
	}
	
}
