package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.dao.ExerciseDao;
import pl.coderslab.model.dao.SolutionDao;
import pl.coderslab.model.dao.UserDao;

@WebServlet("/solution")
public class SolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    SolutionDao solutionDao = new SolutionDao();
	    UserDao userDao = new UserDao();
	    ExerciseDao exerciseDao = new ExerciseDao();
	    int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("solution", solutionDao.loadById(id));
		request.setAttribute("user", userDao.loadById(id));
		request.setAttribute("exercise", exerciseDao.loadById(id));
		getServletContext().getRequestDispatcher("/solution.jsp").forward(request, response);
	}
	
}
