package pl.coderslab.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/panel/logout")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("username");
//		Cookie[] cookies = request.getCookies();
//		if (cookies != null) {
//		    for (Cookie c : cookies) {
//		        if (c.getName().equals("username")) {
//		            c.setMaxAge(0);
//		            response.addCookie(c);
//		            break;
//		        }
//		    }
//		}
		response.sendRedirect(request.getContextPath());
	}

}
