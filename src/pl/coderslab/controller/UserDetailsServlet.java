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
import pl.coderslab.model.dao.ExerciseDao;
import pl.coderslab.model.dao.SolutionDao;
import pl.coderslab.model.dao.UserDao;

@WebServlet("/user-details")
public class UserDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int userId = 0;
	    try {
	        userId = Integer.parseInt(request.getParameter("id"));
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    }

        UserDao userDao = new UserDao();
        SolutionDao solutionDao = new SolutionDao();
        ExerciseDao exerciseDao = new ExerciseDao();
        Map<Integer,Exercise> exercisesMap = new HashMap<>();
        Solution[] solutions = solutionDao.loadAllByUserId(userId);
        for (int i = 0; i < solutions.length; i++) {
            int exerciseId = solutions[i].getExerciseId();
            if ( !exercisesMap.containsKey(exerciseId) ) {
                exercisesMap.put(exerciseId, exerciseDao.loadById(exerciseId));
            }
        }

	    request.setAttribute("user", userDao.loadById(userId));
	    request.setAttribute("solutions", solutionDao.loadAllByUserId(userId));
	    request.setAttribute("exercisesMap", exercisesMap);
		request.getRequestDispatcher("/userDetails.jsp").forward(request, response);
	}

}
