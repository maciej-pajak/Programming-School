package pl.coderslab.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.model.dao.ExerciseDao;
import pl.coderslab.model.dao.SolutionDao;
import pl.coderslab.model.dao.UserDao;
import pl.coderslab.model.standards.DaoInterface;

@WebServlet("/main")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    getLatestSolutions(request, parseLimit( getServletContext().getInitParameter("number-solutions") ) );

	    request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	private static void getLatestSolutions(HttpServletRequest request, int limit) {
	    SolutionDao dao = new SolutionDao();
	    Solution[] solutions = null;
        Map<Integer,User> users = new HashMap<>();
        Map<Integer,Exercise> exercises = new HashMap<>();
        solutions = dao.loadSortedWithLimit(Solution.Column.UPDATED, DaoInterface.SortType.DESC, limit, 0);
        
        if (solutions != null) {
            UserDao userDao = new UserDao();
            ExerciseDao exerciseDao = new ExerciseDao();
            for (int i = 0 ; i < solutions.length ; i++) {
                int userId = solutions[i].getUserId();
                if ( !users.containsKey(userId) ) {
                    users.put(userId, userDao.loadById(userId));
                }   
                
                int exerciseId = solutions[i].getExerciseId();
                if ( !exercises.containsKey(exerciseId) ) {
                    exercises.put(exerciseId, exerciseDao.loadById(exerciseId));
                }
            }
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
