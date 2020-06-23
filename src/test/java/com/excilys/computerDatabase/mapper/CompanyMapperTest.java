package com.excilys.computerDatabase.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.computerDatabase.model.Company;



public class CompanyMapperTest {

    private static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

    private final int id = 42;
    private final String name = "name";

    private ResultSet resultSet = mock(ResultSet.class);

    /**
     * Test that the resultSetis mapped to Company object.
     */
    @Test
    public void testMap() {
    	Company company= new Company();
        try {
            when(resultSet.getInt(ATTRIBUT_ID_COMPANY)).thenReturn(id);
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(name);
            company = CompanyMapper.mapCompany(resultSet);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
		Company expCompany = new Company(id,name);
        assertEquals(expCompany.getId(), company.getId());
        assertEquals(expCompany.getName(), company.getName());
        
    }

    /**
     * Test that the resultSet with id null is mapped to Company object.
     */
    @Test
    public void testMapIdNull() {
    	 Company company= new Company();
        try {
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(name);
            when(resultSet.getInt(ATTRIBUT_ID_COMPANY)).thenReturn(0);
            company = CompanyMapper.mapCompany(resultSet);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
		Company expCompany = new Company(0,name);
        assertEquals(expCompany.getId(), company.getId());
        assertEquals(expCompany.getName(), company.getName());
        
    }

    /**
     * Test that the resultSet with name null is mapped to  in Company object.
     */
    @Test
    public void testMapNameNull() {
    	Company company = new Company();
        try {
            when(resultSet.getInt(ATTRIBUT_ID_COMPANY)).thenReturn(id);
            company = CompanyMapper.mapCompany(resultSet);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        
        Company expCompany = new Company(id,null);

        assertEquals(expCompany.getId(), company.getId());
        assertEquals(expCompany.getName(), company.getName());
    }

   
}
