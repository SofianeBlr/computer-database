import java.sql.*;


public class Test {
	public static void main(String[] args) throws SQLException {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&autoReconnect=true&useSSL=false", "admincdb","qwerty1234");
		Statement myStmt = myConn.createStatement();
		ResultSet myRs = myStmt.executeQuery("select * from computer");
		while(myRs.next()) {
			System.out.println(myRs.getString("name"));
		}
	}
}
