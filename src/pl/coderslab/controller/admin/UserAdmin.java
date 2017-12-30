package pl.coderslab.controller.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.DbUtil;
import pl.coderslab.model.Group;
import pl.coderslab.model.User;

@WebServlet("/panel/users")
public class UserAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ITEMS_ON_PAGE_COOKIE_NAME = "itemsOnPage";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    User[] users = null;
	    int numberOfPages = 1;
	    int currentPage = 1;
	    int itemsOnPage = 5; // TODO default from config
	    String itemsOnPageStr = getCookieValue(ITEMS_ON_PAGE_COOKIE_NAME, request);
	    if (itemsOnPageStr != null) {
	        itemsOnPage = Integer.parseInt(itemsOnPageStr);
	    }
		try (Connection con = DbUtil.getConn()) {
		    
		    numberOfPages = (int) Math.ceil((double)User.getUsersCount(con) / itemsOnPage);
		    String currentPageStr = request.getParameter("page");
	        if (currentPageStr != null) {
	            int tmp = Integer.parseInt(currentPageStr);
	            if (tmp > numberOfPages) {
	                currentPage = numberOfPages;
	            } else if (tmp < 1) {
	                currentPage = 1;
	            } else {
	                currentPage = tmp;
	            }
	        }
		    users = User.loadSortedWithLimit(con, User.Column.USERNAME, User.SortType.ASC, itemsOnPage, (currentPage - 1) * itemsOnPage);
		    System.out.println(numberOfPages);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		request.setAttribute("users", users);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("numberOfPages", numberOfPages);
		
		request.getRequestDispatcher("/panel/users.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try (Connection con = DbUtil.getConn()) {
	        switch (request.getParameter("formType")) {
	        case "new":
	            Group newGroup = new Group(request.getParameter("name"));
	            newGroup.saveToDb(con);
	            break;
	        case "edit":
	            Group editGroup = Group.loadById(con, Integer.parseInt(request.getParameter("userId")));  // TODO number formatting exception
	            editGroup.setName(request.getParameter("newName"));
	            editGroup.saveToDb(con);
	            break;
	        case "delete":
	            Group delGroup = Group.loadById(con, Integer.parseInt(request.getParameter("userId")));
                delGroup.delete(con);
	            break;
	        case "showPages":
	            Cookie c = new Cookie(ITEMS_ON_PAGE_COOKIE_NAME, request.getParameter("show"));
	            response.addCookie(c);
	            break;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    response.sendRedirect(request.getContextPath() + "/panel/users");
	}
	
	private static String getCookieValue(String name, HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    String result = null;
	    for (Cookie c : cookies) {
	        if (c.getName().equals(name)) {
	            result = c.getValue();
	            break;
	        }
	    }
	    return result;
	}

}
