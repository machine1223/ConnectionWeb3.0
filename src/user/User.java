package user;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class User {
	private String name;
	private HttpSession session;
	
	
	public User(String name,HttpSession session)
	{
		this.name = name;
		this.session = session;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public HttpSession getSession()
	{
		return this.session;
	}
}
