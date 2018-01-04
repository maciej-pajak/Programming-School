package pl.coderslab.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.DbUtil;
import pl.coderslab.model.User;
import pl.coderslab.model.dao.UserDao;

@WebServlet("/group-users")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int groupId = Integer.parseInt(request.getParameter("id"));    // TODO format exception
	    UserDao dao = new UserDao(); 
		request.setAttribute("users", dao.loadAllByGroupId(groupId));
		getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
	}

}
