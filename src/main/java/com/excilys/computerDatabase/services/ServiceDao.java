package com.excilys.computerDatabase.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerDatabase.daos.DAO;
import com.excilys.computerDatabase.ui.PageDisplay;

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
	  
	  public List<T> getPage(int page) {
			List<T> list;
			list = dao.getPage(page*10, PageDisplay.NUMBER_PER_PAGE);
			return list;
	 }
		public int getMaxPage(){
			int numberOfPage= (int) Math.ceil(size()/(float) PageDisplay.NUMBER_PER_PAGE);
			return numberOfPage;
		}

}
