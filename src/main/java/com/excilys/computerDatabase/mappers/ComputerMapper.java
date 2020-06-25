package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.models.Computer;

public abstract class ComputerMapper {

	public static Computer mapComputer(ResultSet resultset) throws SQLException {
		Computer comp = new Computer(resultset.getLong("id"),resultset.getString("name"));

		if(Long.valueOf(resultset.getLong("company_id"))!=0L) {
			comp.setCompanyId(resultset.getLong("company_id"));
		}
		if(resultset.getDate("introduced") != null) {
			comp.setIntroduced(resultset.getDate("introduced").toLocalDate());
		}
		if(resultset.getDate("discontinued") != null) {
			comp.setDiscontinued(resultset.getDate("discontinued").toLocalDate());
		}
		return comp;
	}
	public static Computer mapComputerWithCompany(ResultSet resultset) throws SQLException {
		Computer comp = new Computer(resultset.getLong("id"),resultset.getString("name"));
		
		comp.setCompanyName(resultset.getString("company_name"));

		comp.setCompanyName(resultset.getString("company_name"));
		if(Long.valueOf(resultset.getLong("company_id"))!=0L) {
			comp.setCompanyId(resultset.getLong("company_id"));
		}
		if(resultset.getDate("introduced") != null) {
			comp.setIntroduced(resultset.getDate("introduced").toLocalDate());
		}
		if(resultset.getDate("discontinued") != null) {
			comp.setDiscontinued(resultset.getDate("discontinued").toLocalDate());
		}
		return comp;
	}

}
