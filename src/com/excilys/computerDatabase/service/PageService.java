package com.excilys.computerDatabase.service;

import java.util.List;

import com.excilys.computerDatabase.dao.DAO;
import com.excilys.computerDatabase.ui.PageDisplay;

public class PageService<T> {
	DAO<T> dao;
	public PageService(DAO<T> d){
		this.dao = d;
	}
	public List<T> getPage(int page) {
		List<T> list;
		list = dao.getPage(page*10, PageDisplay.NUMBER_PER_PAGE);
		return list;
	}
	public int getMaxPage(){
		int length = dao.size();
		int numberOfPage= (int) Math.ceil(length/(float) PageDisplay.NUMBER_PER_PAGE);
		return numberOfPage;
	}

}
