package pl.coderslab.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USERNAME = "username";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nameLogin = request.getParameter(USERNAME);
		String passLogin = request.getParameter("password");
		String name = getServletContext().getInitParameter(USERNAME);
		String pass = getServletContext().getInitParameter("password");

		if ( pass.equals(passLogin) && name.equals(nameLogin) ) {
		    HttpSession sess = request.getSession();
		    sess.setAttribute(USERNAME, name);
    
		    response.sendRedirect("panel/groups");
		} else {
		    response.sendRedirect("login.jsp");
		}

	}

}
