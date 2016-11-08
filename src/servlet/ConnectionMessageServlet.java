package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.ConnectionManager;
import connection.Word;

/**
 * Servlet implementation class ConnectionMessageServlet
 */
@WebServlet("/ConnectionMessageServlet")
public class ConnectionMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectionMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session  =request.getSession();
		ConnectionManager cm = (ConnectionManager)session.getAttribute("connectionManager");
		
		String way = request.getParameter("usage");
		if(way.equals("set"))
		{
			cm.addWord(new Word(request.getParameter("orignName"),request.getParameter("targetName"),request.getParameter("content"),request.getParameter("time")));
		}
		else if(way.equals("get"))
		{
			PrintWriter out = response.getWriter();
			ArrayList<Word> result = cm.getWords(request.getParameter("connectiongName"));
			for(Word word:result)
			{
				String temp = word.orignName + "(" + word.date + "):" + word.content;
				temp = temp.replaceAll(":", ">");
				out.println(URLEncoder.encode(temp, "utf-8"));
			}
			out.close();
		}
	}

}
