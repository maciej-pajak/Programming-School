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
import pl.coderslab.model.Group;

@WebServlet("/groups")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Group[] groups;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    loadGroups();
	    request.setAttribute("groups", groups);
		getServletContext().getRequestDispatcher("/groups.jsp").forward(request, response);
	}
	
	private static void loadGroups() {
	    try ( Connection con = DbUtil.getConn() ) {
	        groups = Group.loadAll(con);
	    } catch (SQLException e) {
	        groups = null;
	        e.printStackTrace();
	    }
	}

}
