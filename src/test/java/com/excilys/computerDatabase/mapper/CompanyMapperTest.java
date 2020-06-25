package com.excilys.computerDatabase.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.models.Company;



public class CompanyMapperTest {

    private static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

    private final Long id = 42L;
    private final String name = "name";

    private ResultSet resultSet = mock(ResultSet.class);

    /**
     * Test that the resultSetis mapped to Company object.
     */
    @Test
    public void testMap() {
    	Company company= new Company();
        try {
            when(resultSet.getLong(ATTRIBUT_ID_COMPANY)).thenReturn(id);
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
            when(resultSet.getLong(ATTRIBUT_ID_COMPANY)).thenReturn(0L);
            company = CompanyMapper.mapCompany(resultSet);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
		Company expCompany = new Company(0L,name);
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
            when(resultSet.getLong(ATTRIBUT_ID_COMPANY)).thenReturn(id);
            company = CompanyMapper.mapCompany(resultSet);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        
        Company expCompany = new Company(id,null);

        assertEquals(expCompany.getId(), company.getId());
        assertEquals(expCompany.getName(), company.getName());
    }

   
}
