package pl.coderslab.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.dao.ExerciseDao;
import pl.coderslab.model.standards.ColumnsEnumInterface;

/**
 * Servlet implementation class ExerciseAdmin
 */
@WebServlet("/panel/exercises")
public class ExerciseAdmin extends AbstractServlet<Exercise, ExerciseDao> {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

    @Override
    protected String getJspPath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ColumnsEnumInterface getSortDefault() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Exercise createObject(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Exercise editObject(HttpServletRequest request, Exercise object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ColumnsEnumInterface getSortByColumn(String param) {
        // TODO Auto-generated method stub
        return null;
    }

}
