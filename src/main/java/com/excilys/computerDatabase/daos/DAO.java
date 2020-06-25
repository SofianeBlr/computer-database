package com.excilys.computerDatabase.daos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;





public abstract class DAO<T> {
	protected static Logger logger = LoggerFactory.getLogger(DAO.class);
	protected static Connection connect = null;
    private static HikariDataSource dataSource;

	public DAO(){
		if (connect== null) {
			init();
		}
	}
	private void init() {
		try {
			HikariConfig config = new HikariConfig("/datasource.properties");
            dataSource = new HikariDataSource(config);
			connect = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("Unable to establish connection to database");
		}
	}



	protected Connection getConnection() throws SQLException {
		if(connect.isClosed()) {
			connect = dataSource.getConnection();
		}
		return connect;
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
	public abstract T find(Long id);

	/**
	 * Method to get max id in database
	 * @return int maxId
	 */
	public abstract Long maxId() throws SQLException;


	/**
	 * Get nbr of object in database
	 * @return nbr of object
	 */
	public abstract Long size();
	/**
	 * Method get a precise page from database
	 * @param debut start of the page(offset)
	 * @param number number of element to return
	 * @return List<T> page
	 */
	public abstract ArrayList<T> getPage(int debut,int number);



	public static void close(AutoCloseable connection) {
		if(connection!=null) {
			try {
				connection.close();

			}catch(Exception e) {
			}
		}
	}





}