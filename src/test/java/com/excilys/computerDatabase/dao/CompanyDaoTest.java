package com.excilys.computerDatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.excilys.computerDatabase.daos.CompanyDao;
import com.excilys.computerDatabase.daos.DAO;
import com.excilys.computerDatabase.models.Company;

public class CompanyDaoTest {
	
	private CompanyDao companyDao;
	
    @Before
    public void setup()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
       
        Field instanceDao = DAO.class.getDeclaredField("connect");
        instanceDao.setAccessible(true);
        instanceDao.set(null, null);
        companyDao = new CompanyDao();
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
		ArrayList<Company> c = companyDao.getPage(0, 10,null);
	    assertEquals(10, c.size());
	    
	}
	
	@Test
	public void testGetAll() {
		ArrayList<Company> c = companyDao.getAll();
	    assertEquals( 10, c.size());
	}

}
