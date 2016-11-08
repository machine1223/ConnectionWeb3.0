package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import connection.Word;

public class DatabaseManager {
	private Database database;
	
	public DatabaseManager()
	{
		this.database = new Database();
	}
	
	@SuppressWarnings("finally")
	public String getKey(String name)
	{
		String key = "";
		String sqlStr = "select * from tbuser where name = ?";
		Connection conn = this.database.getConnection();
		PreparedStatement ppStatement = this.database.getPreparedStatement(sqlStr,conn);
		
		try{
			ppStatement.setString(1, name);
			ResultSet rs = ppStatement.executeQuery();
			if(rs.next())
//				key = rs.getString("password");
				key = rs.getString("keyword");
			rs.close();
			ppStatement.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return key.trim();
		}
	}
	
	@SuppressWarnings("finally")
	public boolean isExit(String name)
	{
		boolean flag = false;
		Connection conn = this.database.getConnection();
		PreparedStatement pps = this.database.getPreparedStatement("select * from tbuser where name = ?",conn);
		try{
			pps.setString(1, name);
			ResultSet rs = pps.executeQuery();
			if(rs.next())
				flag = true;
			rs.close();
			pps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return flag; 
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<String> getAllNames()
	{
		ArrayList<String> names = new ArrayList<String>();
		Connection conn = this.database.getConnection();
		PreparedStatement ppStatement = this.database.getPreparedStatement("select * from tbuser",conn);
		try{
			ResultSet rs = ppStatement.executeQuery();
			while(rs.next())
				names.add(rs.getString("name"));
			rs.close();
			ppStatement.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return names;
		}
	}
	
	@SuppressWarnings("finally")
	public  ArrayList<Word> getContent(String name)
	{
		ArrayList<Word> content = new ArrayList<Word>();
		Connection conn = this.database.getConnection();
		PreparedStatement pps = this.database.getPreparedStatement("select * from tbconnection where orignName = ? or targetName = ?",conn);
		try{
			pps.setString(1, name);
			pps.setString(2, name);
			ResultSet rs = pps.executeQuery();
			while(rs.next())
				content.add(new Word(rs.getString("orignName"),rs.getString("targetName"),rs.getString("content"),rs.getTimestamp("date").toString()));
			rs.close();
			pps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return content;
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<Word> getContent(String name1,String name2)
	{
		ArrayList<Word> result = new ArrayList<Word>();
		Connection conn = this.database.getConnection();
		PreparedStatement pps = this.database.getPreparedStatement("select * from tbconnection where (orignName = ? and targetName = ?) or (orignName = ? and targetName = ?)",conn);
		try{
			pps.setString(1, name1);
			pps.setString(2, name2);
			pps.setString(3, name2);
			pps.setString(4, name1);
			ResultSet rs = pps.executeQuery();
			while(rs.next())
				result.add(new Word(rs.getString("orignName"),rs.getString("targetName"),rs.getString("content"),rs.getTimestamp("date").toString()));
			rs.close();
			pps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return result;
		}
	}

	@SuppressWarnings("finally")
	public boolean addUser(String name,String password)
	{
		boolean flag = false;
		if(this.isExit(name))
			return flag;
		Connection conn = this.database.getConnection();
		PreparedStatement pps = this.database.getPreparedStatement("insert into tbuser values(?,?)",conn);
		try{
			pps.setString(1, name);
			pps.setString(2, password);	
			if( pps.executeUpdate()  != 0)
				flag = true;
			pps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return flag;
		}
	}
	
	public void removeUser(String name)
	{
		Connection conn = this.database.getConnection();
		PreparedStatement pps = this.database.getPreparedStatement("delete from tbuser where name = ?",conn);
		try{
			pps.setString(1, name);
			pps.executeUpdate();
			pps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addContent(String orign,String target,String content)
	{
		long id = this.getCount() + 1;
		Connection conn = this.database.getConnection();
		PreparedStatement pps = this.database.getPreparedStatement("insert into tbconnection values(?,?,?,?,?)",conn);
		try{
			pps.setLong(1, id);
			pps.setString(2, orign);
			pps.setString(3, target);
			pps.setString(4, content);
			pps.setString(5, this.getSystemTime());
			pps.executeUpdate();
			pps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("finally")
	public long getCount()
	{
		long result = 0;
		Connection conn = this.database.getConnection();
		PreparedStatement pps = this.database.getPreparedStatement("select max(id) as id from tbconnection",conn);
		try{
			ResultSet rs = pps.executeQuery();
			if(rs.next())
				result = rs.getLong("id");
			rs.close();
			pps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	private String getSystemTime()
	{
		Calendar c = Calendar.getInstance();
		String date = String.valueOf(c.get(Calendar.YEAR)) + String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DATE))
									+ String.valueOf(c.get(Calendar.HOUR)) + String.valueOf(c.get(Calendar.MINUTE)) + String.valueOf(c.get(Calendar.SECOND));
		return date;
	}
}
