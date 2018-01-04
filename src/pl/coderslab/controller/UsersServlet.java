package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.dao.UserDao;

@WebServlet("/group-users")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int groupId = 0;
	    try {
	        groupId = Integer.parseInt(request.getParameter("id"));
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    }
	    UserDao dao = new UserDao(); 
		request.setAttribute("users", dao.loadAllByGroupId(groupId));
		getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
	}

}
