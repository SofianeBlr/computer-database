package com.excilys.computerDatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.excilys.computerDatabase.model.Company;

public class CompanyDaoTest {
	
	/**
     * Reset companyDao.
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Before
    public void setup()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
       
        Field instanceCompanyDao = CompanyDao.class.getDeclaredField("companyDao");
        instanceCompanyDao.setAccessible(true);
        instanceCompanyDao.set(null, null);
        Field instanceDao = DAO.class.getDeclaredField("connect");
        instanceDao.setAccessible(true);
        instanceDao.set(null, null);
    }
	

	@Test
	public void testGetInstanceCompany() {
		CompanyDao companyDao= CompanyDao.getInstance();
		assertNotNull(companyDao);
		assertEquals("The two instance are different (not singleton).", CompanyDao.getInstance(), companyDao);
	}
	
	@Test
	public void testCreateCompany() {
		CompanyDao companyDao= CompanyDao.getInstance();
		Company company = new Company(0,"test");
		Company companyExc = companyDao.create(company);
	    assertEquals(companyExc.getName(), company.getName());
	}
	
	@Test
	public void testDeleteCompany() {
		CompanyDao companyDao= CompanyDao.getInstance();
		Company company = new Company(42,"test");
		companyDao.delete(company);
	    assertNull(companyDao.find(company.getId()));
	}
	
	@Test
	public void testGetPageCompany() {
		CompanyDao companyDao= CompanyDao.getInstance();
		ArrayList<Company> c = companyDao.getPage(0, 10);
	    assertEquals( 10, c.size());
	    
	}
	
	@Test
	public void testGetAll() {
		CompanyDao companyDao= CompanyDao.getInstance();
		ArrayList<Company> c = companyDao.getAll();
	    assertEquals( 10, c.size());
	}

}
