package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import datasource.DatabaseManager;
import user.UsersManager;

public class MyServerletContextListener implements ServletContextListener {

	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
		ServletContext context = arg0.getServletContext();
		context.setAttribute("usersManager", new UsersManager());
		context.setAttribute("DatabaseManager", new DatabaseManager());
	}

}
