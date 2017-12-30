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

@WebServlet("/main")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    getLatestSolutions(request, parseLimit( getServletContext().getInitParameter("number-solutions") ) );
	    request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	private static void getLatestSolutions(HttpServletRequest request, int limit) {
	    Solution[] solutions = null;
        Map<Integer,User> users = new HashMap<>();
        Map<Integer,Exercise> exercises = new HashMap<>();
        try ( Connection con = DbUtil.getConn() ) {
            solutions = Solution.loadAll(con, limit);
            
            for (int i = 0 ; i < solutions.length ; i++) {
                int userId = solutions[i].getUserId();
                if ( !users.containsKey(userId) ) {
                    users.put(userId, User.loadById(con, userId));
                }
                
                int exerciseId = solutions[i].getExerciseId();
                if ( !exercises.containsKey(exerciseId) ) {
                    exercises.put(exerciseId, Exercise.loadById(con, exerciseId));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        request.setAttribute("latestSolutions", solutions);
        request.setAttribute("latestUsers", users);
        request.setAttribute("latestExercises", exercises);
	}
	
	private static int parseLimit(String limit) {
	    int result = 5;        // default value
	    try {
	        result = Integer.parseInt(limit);
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    }
	    return result;
	}

}
