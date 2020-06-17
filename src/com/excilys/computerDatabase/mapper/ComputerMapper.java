package com.excilys.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.model.Computer;

public class ComputerMapper {

	public static Computer mapComputer(ResultSet resultset) throws SQLException {
		Computer comp = new Computer(resultset.getInt("id"),resultset.getString("name"),resultset.getInt("company_id"));
		if(resultset.getDate("introduced") != null) {
			comp.setIntroduced(resultset.getDate("introduced").toLocalDate());
		}
		if(resultset.getDate("discontinued") != null) {
			comp.setDiscontinued(resultset.getDate("discontinued").toLocalDate());
		}
		return comp;
	}

}
