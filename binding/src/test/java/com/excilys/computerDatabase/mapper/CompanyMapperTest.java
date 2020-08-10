package com.excilys.computerDatabase.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.excilys.computerDatabase.dtos.CompanyDto;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.models.Company;



public class CompanyMapperTest {


    /**
     * Test that the resultSetis mapped to Company object.
     */
   
    @Test
    public void testMapToDto() {
    	Company company = new Company.CompanyBuilder()
    			.setIdBuild(10L)
    			.setNameBuild("testCompany")
    			.build();
        CompanyDto companyDto = CompanyMapper.mapCompanyDto(company);

        assertEquals(companyDto.getId(), company.getId().toString());
        assertEquals(companyDto.getName(), company.getName());
    }
    @Test
    public void testMapToDtoWNull() {
    	Company company = new Company.CompanyBuilder()
    			
    			.setNameBuild("testCompany")
    			.build();
        CompanyDto companyDto = CompanyMapper.mapCompanyDto(company);
        assertNull(companyDto.getName());
    }

   
}
