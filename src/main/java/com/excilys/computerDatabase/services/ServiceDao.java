package com.excilys.computerDatabase.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerDatabase.daos.DAO;

public abstract class ServiceDao<T> {

	protected DAO<T> dao;

	public ServiceDao(DAO<T> dao){
		this.dao = dao;
	}

	/**
	 * GetAll database
	 * @return list of object from database 
	 */
	public ArrayList<T> getAll(){
		return dao.getAll();
	}

	/**
	 * Create a new object in database
	 * @param obj to create
	 * @return obj created
	 */
	public T create(T obj) {
		return dao.create(obj);
	}

	/**
	 * delete object in database
	 * @param object to delete
	 * @return boolean (true if deleted)
	 */
	public boolean delete(T obj) {
		return dao.delete(obj);
	}

	/**
	 * update an object in database
	 * @param obj to update
	 * @return Obj updated
	 */
	public T update(T obj) {
		return dao.update(obj);
	}

	/**
	 * find an object from id
	 * @param id
	 * @return T obj find
	 */
	public T find(Long id) {
		return dao.find(id);
	}

	/**
	 * Method to get max id in database
	 * @return int maxId
	 */
	public Long maxId() throws SQLException{
		return dao.maxId();
	}


	/**
	 * Get nbr of object in database
	 * @return nbr of object
	 */
	public Long size() {
		return dao.size();
	}
	public Long sizeWithSearch(String search) {
		return dao.sizeWithSearch(search);
	}

	public List<T> getPage(int page,int number) {
		List<T> list;
		list = dao.getPage(page*number, number);
		return list;
	}
	public List<T> getPage(int page,int number,String search) {
		List<T> list;
		list = dao.getPageWithSearch(page*number, number,search);
		return list;
	}
	public int getMaxPage(int number){
		int numberOfPage= (int) Math.ceil(size()/(float) number);
		return numberOfPage;
	}
	public int getMaxPageWithSearch(int number,String search){
		int numberOfPage= (int) Math.ceil(sizeWithSearch(search)/(float) number);
		return numberOfPage;
	}

}
