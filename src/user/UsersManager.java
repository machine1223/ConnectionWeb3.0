package user;

import java.util.ArrayList;

public class UsersManager {
	public ArrayList<User> users;
	
	public UsersManager()
	{
		users = new ArrayList<User>();
	}
	
	public boolean isExit(String name)
	{
		for (User user : users) 
		{
			if(user.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public boolean isExit(User temp)
	{
		return this.isExit(temp.getName());
	}
	
	public User getUser(String name)
	{
		if(! this.isExit(name))
			return null;
		for(User user:users)
		{
			if(user.getName().equals(name))
				return user;
		}
		return null;
	}
	
	public int getNumber()
	{
		return this.users.size();
	}
	
	public boolean addUser(User user)
	{
		if(this.isExit(user.getName()))
			return false;
		this.users.add(user);
		return true;
	}
	
	public void removeUser(User user)
	{
		this.users.remove(user);
	}
}
