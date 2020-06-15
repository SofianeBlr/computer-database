package com.excilys.computerDatabase.Dao;
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
   * GetAll
   * @param obj
   * @return boolean 
   */
   public abstract ArrayList<T> getAll();
   
  /**
  * M�thode de creation
  * @param obj
  * @return boolean 
  */
  public abstract boolean create(T obj);

  /**
  * M�thode pour effacer
  * @param obj
  * @return boolean 
  */
  public abstract boolean delete(T obj);

  /**
  * M�thode de mise � jour
  * @param obj
  * @return boolean
  */
  public abstract boolean update(T obj);

  /**
  * M�thode de recherche des informations
  * @param id
  * @return T
  */
  public abstract T find(int id);
}