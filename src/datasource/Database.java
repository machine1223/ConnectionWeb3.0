package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
	
	public Database()
	{
		 
	}
	
	@SuppressWarnings("finally")
	public Connection getConnection()
	{
		Connection conn = null;;
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/connectionweb", "root", "");
            System.out.print("Success connect to Database");
        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
        }finally{
        	return conn;
        }
	}
	
	@SuppressWarnings("finally")
	public PreparedStatement getPreparedStatement(String sqlStr,Connection conn)
	{
		PreparedStatement statement = null;
		try{
			statement = conn.prepareStatement(sqlStr);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return statement;
		}
	}
}
