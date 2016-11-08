package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.ConnectionManager;
import datasource.DatabaseManager;
import user.User;
import user.UsersManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		if(session.getAttribute("user") != null)
			out.print("true");
		else
			out.print("false");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = request.getServletContext();
		UsersManager usersManager = (UsersManager)context.getAttribute("usersManager");
		DatabaseManager dm = (DatabaseManager)context.getAttribute("DatabaseManager");
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		System.out.println("name=" + name + "password=" + password);
		
		if(dm.getKey(name).equals(password))
		{
			User user = new User(name,session);
			usersManager.addUser(user);
			session.setAttribute("user", user);
			session.setAttribute("connectionManager", new ConnectionManager());
			out.print("true");
		}
		else
		{
			out.print("false");
		}
		out.close();
	}

}
