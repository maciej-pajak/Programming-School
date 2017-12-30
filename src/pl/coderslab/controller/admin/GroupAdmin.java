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

@WebServlet("/panel/groups")
public class GroupAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ITEMS_ON_PAGE_COOKIE_NAME = "itemsOnPage";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Group[] groups = null;
	    int numberOfPages = 1;
	    int currentPage = 1;
	    int itemsOnPage = 5; // TODO default from config
	    String itemsOnPageStr = getCookieValue(ITEMS_ON_PAGE_COOKIE_NAME, request);
	    if (itemsOnPageStr != null) {
	        itemsOnPage = Integer.parseInt(itemsOnPageStr);
	    }
		try (Connection con = DbUtil.getConn()) {
		    
		    System.out.println("itemsOnPage: " + itemsOnPage);
		    numberOfPages = (int) Math.ceil((double)Group.getGroupCount(con) / itemsOnPage);
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
		    groups = Group.loadSortedWithLimit(con, Group.Column.NAME, Group.SortType.ASC, itemsOnPage, (currentPage - 1) * itemsOnPage);
		    System.out.println(numberOfPages);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		request.setAttribute("groups", groups);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("numberOfPages", numberOfPages);
		
		request.getRequestDispatcher("/panel/groups.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try (Connection con = DbUtil.getConn()) {
	        switch (request.getParameter("formType")) {
	        case "new":
	            Group newGroup = new Group(request.getParameter("name"));
	            newGroup.saveToDb(con);
	            break;
	        case "edit":
	            Group editGroup = Group.loadById(con, Integer.parseInt(request.getParameter("groupId")));  // TODO number formatting exception
	            editGroup.setName(request.getParameter("newName"));
	            editGroup.saveToDb(con);
	            break;
	        case "delete":
	            Group delGroup = Group.loadById(con, Integer.parseInt(request.getParameter("groupId")));
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
	    response.sendRedirect(request.getContextPath() + "/panel/groups");
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
