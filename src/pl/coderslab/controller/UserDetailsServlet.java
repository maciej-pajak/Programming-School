package pl.coderslab.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.DbUtil;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/user-details")
public class UserDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static User user;
	private static Solution[] solutions;
	private static Map<Integer,Exercise> exercisesMap;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try (Connection con = DbUtil.getConn()) {
	        int userId = Integer.parseInt(request.getParameter("id"));     // TODO null pointer exception
	        user = User.loadById(con, userId);
	        solutions = Solution.loadAllByUserId(con, userId);
	        exercisesMap = new HashMap<>();
	        for (int i = 0; i < solutions.length; i++) {
	            int exerciseId = solutions[i].getExerciseId();
                if ( !exercisesMap.containsKey(exerciseId) ) {
                    exercisesMap.put(exerciseId, Exercise.loadById(con, exerciseId));
                }
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    request.setAttribute("user", user);
	    request.setAttribute("solutions", solutions);
	    request.setAttribute("exercisesMap", exercisesMap);
		request.getRequestDispatcher("/userDetails.jsp").forward(request, response);
	}

}
