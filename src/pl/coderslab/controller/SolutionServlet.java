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
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/solution")
public class SolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Solution solution;
	private static User user;
	private static Exercise exercise;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    solution = null;
		user = null;
		exercise = null;
		loadInfo(request);
		request.setAttribute("solution", solution);
		request.setAttribute("user", user);
		request.setAttribute("exercise", exercise);
		getServletContext().getRequestDispatcher("/solution.jsp").forward(request, response);
	}
	
	private static void loadInfo(HttpServletRequest request) {
	    try ( Connection con = DbUtil.getConn() ) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                solution = Solution.loadById(con, id);
                if (solution != null) {
                    user = User.loadById(con, solution.getUserId());
                exercise = Exercise.loadById(con, solution.getExerciseId());
                }
            } catch (NumberFormatException e) {}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
    }
	}

}
