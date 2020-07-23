package com.excilys.computerDatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerDatabase.config.SpringConfigDao;
import com.excilys.computerDatabase.daos.CompanyDao;
import com.excilys.computerDatabase.models.Company;
import com.excilys.computerDatabase.models.Page;

public class CompanyDaoTest {
	
	private CompanyDao companyDao;
	
    @Before
    public void setup()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfigDao.class);
    	companyDao = ctx.getBean(CompanyDao.class);
        
    }
	

	
	
	@Test
	public void testCreateCompany() {
		Company company = new Company(0L,"test");
		Company companyExc = companyDao.create(company);
	    assertEquals(companyExc.getName(), company.getName());
	}
	
	@Test
	public void testDeleteCompany() {
		Company company = new Company(42L,"test");
		companyDao.delete(company.getId());
	    assertNull(companyDao.find(company.getId()));
	}
	@Test
	public void testUpdateCompany() {
		Company company = companyDao.find(1L);
		company.setName("test");
		Company expCompany = companyDao.update(company);
	    assertEquals(company.getName(),expCompany.getName());
	    assertEquals(company.getId(),expCompany.getId());
	}
	
	@Test
	public void testDeleteCompanyWithComputers() {
		companyDao.delete(1L);
	    assertEquals(new Long(9), companyDao.size());
	}
	
	@Test
	public void testGetPageCompany() {
		ArrayList<Company> c = companyDao.getPage(new Page(0,10));
	    assertEquals(10, c.size());
	    
	}
	
	@Test
	public void testGetAll() {
		ArrayList<Company> c = companyDao.getAll();
	    assertEquals( 10, c.size());
	}

}
