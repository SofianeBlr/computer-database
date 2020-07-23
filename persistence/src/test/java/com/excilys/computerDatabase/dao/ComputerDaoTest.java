package com.excilys.computerDatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerDatabase.config.SpringConfigDao;
import com.excilys.computerDatabase.daos.ComputerDao;
import com.excilys.computerDatabase.models.Computer;
import com.excilys.computerDatabase.models.Page;
@SuppressWarnings("resource")
public class ComputerDaoTest {
	private ComputerDao computerDao;
	
    @Before
    public void setup()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
       
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfigDao.class);
        computerDao = ctx.getBean(ComputerDao.class);
    }
	
	
	@Test
	public void testCreateComputer() {
		Computer computer = new Computer(0L,"test",LocalDate.now());
		computer.setCompany(null);
		Computer computerExc = computerDao.create(computer);
	    assertEquals(computerExc, computer);
	}
	@Test
	public void testCreateComputerWNull() {
		Computer computer = new Computer(0L,"test",null,LocalDate.now(),10L);
		Computer computerExc = computerDao.create(computer);
	    assertEquals(computerExc, computer);
	}
	
	@Test
	public void testUpdateComputer() {
		Computer computer = new Computer(10L,"test",null,LocalDate.now(),10L);
		Computer computerExc = computerDao.update(computer);
	    assertEquals(computerExc, computer);
	}
	
	@Test
	public void testUpdateComputerWNull() {
		Computer computer = new Computer(10L,"test",LocalDate.now(),null,null);
		Computer computerExc = computerDao.update(computer);
	    assertEquals(computerExc, computer);
	}
	
	@Test
	public void testDeleteComputer() {
		Computer computer = new Computer(42L,"test");
		computerDao.delete(computer.getId());
	    assertNull(computerDao.find(computer.getId()));
	}
	
	@Test
	public void testGetPageComputer() {
		ArrayList<Computer> c = computerDao.getPage(new Page(0,10));
	    assertEquals( 10, c.size());
	    
	}
	
	@Test
	public void testGetAll() {
		ArrayList<Computer> c = computerDao.getAll();
	    assertEquals( 50, c.size());
	}
	
	@Test
	public void testGetPageWithSearchComputerWithOrderBy() {
		ArrayList<Computer> c = computerDao.getPageWithSearch(new Page(0, 100, "App","cnASC"));
		assertTrue(c.get(10).getName().compareTo(c.get(11).getName())<=0); 
	}
	@Test
	public void testGetMaxPageWithSearchComputer() {
		Long c = computerDao.sizeWithSearch("App");
	    assertEquals( new Long(23), c);
	    
	}
	
	@Test
	public void testGetMaxPageWithWithOrderBy() {
		ArrayList<Computer> c = computerDao.getPage(new Page(0, 20,null, "cnDSC"));
	    assertTrue(c.get(1).getName().compareTo(c.get(2).getName())>=0); 
	}
	@Test
	public void testGetSize() {
		Long c = computerDao.size();
	    assertEquals( new Long(50), c);
	    
	}

}
