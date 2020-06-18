package com.excilys.computerDatabase.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;



public abstract class DAO<T> {
  protected static Connection connect = null;
   
  public DAO(){
    if (connect== null) {
    	try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&autoReconnect=true&useSSL=false", "admincdb","qwerty1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  }
  
  /**
   * GetAll database
   * @return list of object from database 
   */
   public abstract ArrayList<T> getAll();
   
  /**
  * Create a new object in database
  * @param obj to create
  * @return obj created
  */
  public abstract T create(T obj);

  /**
  * delete object in database
  * @param object to delete
  * @return boolean (true if deleted)
  */
  public abstract boolean delete(T obj);

  /**
  * update an object in database
  * @param obj to update
  * @return Obj updated
  */
  public abstract T update(T obj);

  /**
  * find an object from id
  * @param id
  * @return T obj find
  */
  public abstract T find(int id);
  
  /**
   * Method to get max id in database
   * @return int maxId
   */
  public abstract int maxId() throws SQLException;
  
  
  /**
   * Get nbr of object in database
   * @return nbr of object
   */
  public abstract int size();
  /**
   * Method get a precise page from database
   * @param debut start of the page(offset)
   * @param number number of element to return
   * @return List<T> page
   */
  public abstract ArrayList<T> getPage(int debut,int number);
  
}