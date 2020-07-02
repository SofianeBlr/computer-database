package com.excilys.computerDatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.excilys.computerDatabase.daos.ComputerDao;
import com.excilys.computerDatabase.daos.DAO;
import com.excilys.computerDatabase.models.Computer;

public class ComputerDaoTest {
	
	
    @Before
    public void setup()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
       
        Field instanceComputerDao = ComputerDao.class.getDeclaredField("computerDao");
        instanceComputerDao.setAccessible(true);
        instanceComputerDao.set(null, null);
        Field instanceDao = DAO.class.getDeclaredField("connect");
        instanceDao.setAccessible(true);
        instanceDao.set(null, null);
    }
	

	@Test
	public void testGetInstanceComputer() {
		ComputerDao computerDao= ComputerDao.getInstance();
		assertNotNull(computerDao);
		assertEquals("The two instance are different (not singleton).", ComputerDao.getInstance(), computerDao);
	}
	
	@Test
	public void testCreateComputer() {
		ComputerDao computerDao= ComputerDao.getInstance();
		Computer computer = new Computer(0L,"test",LocalDate.now());
		Computer computerExc = computerDao.create(computer);
	    assertEquals(computerExc, computer);
	}
	@Test
	public void testCreateComputerWNull() {
		ComputerDao computerDao= ComputerDao.getInstance();
		Computer computer = new Computer(0L,"test",null,LocalDate.now(),10L);
		Computer computerExc = computerDao.create(computer);
	    assertEquals(computerExc, computer);
	}
	
	@Test
	public void testUpdateComputer() {
		ComputerDao computerDao= ComputerDao.getInstance();
		Computer computer = new Computer(10L,"test",null,LocalDate.now(),10L);
		Computer computerExc = computerDao.update(computer);
	    assertEquals(computerExc, computer);
	}
	
	@Test
	public void testUpdateComputerWNull() {
		ComputerDao computerDao= ComputerDao.getInstance();
		Computer computer = new Computer(10L,"test",LocalDate.now(),null,null);
		Computer computerExc = computerDao.update(computer);
	    assertEquals(computerExc, computer);
	}
	
	@Test
	public void testDeleteComputer() {
		ComputerDao computerDao= ComputerDao.getInstance();
		Computer computer = new Computer(42L,"test");
		computerDao.delete(computer.getId());
	    assertNull(computerDao.find(computer.getId()));
	}
	
	@Test
	public void testGetPageComputer() {
		ComputerDao computerDao= ComputerDao.getInstance();
		ArrayList<Computer> c = computerDao.getPage(0, 10,null);
	    assertEquals( 10, c.size());
	    
	}
	
	@Test
	public void testGetAll() {
		ComputerDao computerDao= ComputerDao.getInstance();
		ArrayList<Computer> c = computerDao.getAll();
	    assertEquals( 50, c.size());
	}
	
	@Test
	public void testGetPageWithSearchComputerWithOrderBy() {
		ComputerDao computerDao= ComputerDao.getInstance();
		ArrayList<Computer> c = computerDao.getPageWithSearch(0, 100, "App","cnASC");
		assertTrue(c.get(10).getName().compareTo(c.get(11).getName())<=0); 
	}
	@Test
	public void testGetMaxPageWithSearchComputer() {
		ComputerDao computerDao= ComputerDao.getInstance();
		Long c = computerDao.sizeWithSearch("App");
	    assertEquals( new Long(23), c);
	    
	}
	
	@Test
	public void testGetMaxPageWithWithOrderBy() {
		ComputerDao computerDao= ComputerDao.getInstance();
		ArrayList<Computer> c = computerDao.getPage(0, 20, "cnDSC");
	    assertTrue(c.get(1).getName().compareTo(c.get(2).getName())>=0); 
	}
	@Test
	public void testGetSize() {
		ComputerDao computerDao= ComputerDao.getInstance();
		Long c = computerDao.size();
	    assertEquals( new Long(50), c);
	    
	}

}
