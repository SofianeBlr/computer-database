package com.excilys.computerDatabase.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public abstract class DAO<T> {
  protected static Connection connect = null;
   
  public DAO(){
    if (connect== null) {
    	init();
    }
  }
  private void init() {
	  
	  Properties properties = new Properties();
      try {
          properties.load(new FileInputStream("src/ressource/database.properties"));
      } catch (IOException e) {
    	  System.out.println("Unable to find to find the database.properties file");
      }
      try {
			connect = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),properties.getProperty("password"));
		} catch (SQLException e) {
			System.out.println("Unable to establish connection to database");
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