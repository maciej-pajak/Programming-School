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

@WebServlet("/group-users")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static User[] users;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DbUtil.getConn()) {
		    int groupId = Integer.parseInt(request.getParameter("id"));
		    users = User.loadAllByGroupId(con, groupId);
		} catch (SQLException e) {
		    e.printStackTrace();
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		request.setAttribute("users", users);
		getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
	}
	
	private static void loadUsers() {  // TODO
	    
	}

}
