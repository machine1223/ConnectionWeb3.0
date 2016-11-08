package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.User;
import user.UsersManager;

/**
 * Servlet implementation class UserMessageServlet
 */
@WebServlet("/UserMessageServlet")
public class UserMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ServletContext context = (ServletContext)request.getServletContext();
		
		UsersManager um = (UsersManager)context.getAttribute("usersManager");
		User user = (User)session.getAttribute("user");
		
		PrintWriter out = response.getWriter();
		
		String result = "name>" + user.getName() + "!onlineNumber>" + String.valueOf(um.getNumber()) + "!onlineAccount>";
		ArrayList<User> users = um.users;
		for(User temp:users)
		{
			result += temp.getName() + "-";
		}
		result = result.substring(0, result.length() - 1);
		
		out.print(URLEncoder.encode(result, "utf-8"));
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
