package pl.coderslab.controller.admin;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.dao.ExerciseDao;
import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DaoInterface;
import pl.coderslab.model.standards.DaoInterface.SortType;

@WebServlet("/panel/exercises")
public class ExerciseAdmin extends AbstractServlet<Exercise> {
	private static final long serialVersionUID = 1L;

    @Override
    protected DaoInterface<Exercise> getDao() {
        return new ExerciseDao();
    }

    @Override
    protected String[][] tableHeader() {
        return new String[][] { {"Title", "TITLE"}, {"Description", "DESCRIPTION"} };
    }

    @Override
    protected Map<Integer, String[]> table(ColumnsEnumInterface sortBy, SortType sortType, int limit, int offset) {
        ExerciseDao dao = new ExerciseDao();
        Exercise[] exercises = dao.loadSortedWithLimit(sortBy, sortType, limit, offset);
        Map<Integer, String[]> table = new LinkedHashMap<>();
        for (int i = 0 ; i < exercises.length ; i++) {
            String[] row = new String[2];
            row[0] = exercises[i].getTitle();
            row[1] = exercises[i].getDescription();
            table.put(exercises[i].getId(), row);
        }
        return table;
    }

    @Override
    protected ColumnsEnumInterface getSortDefault() {
        return Exercise.Column.TITLE;
    }

    @Override
    protected ColumnsEnumInterface getSortByColumn(String param) {
        return Enum.valueOf(Exercise.Column.class, param);
    }

    @Override
    protected Exercise createObject(HttpServletRequest request) {
        return new Exercise(request.getParameter("title"), request.getParameter("description"));
    }

    @Override
    protected Exercise editObject(HttpServletRequest request, Exercise object) {
        String title = request.getParameter("title");
        if (!title.isEmpty()) {
            object.setTitle(title);
        }
        String desc = request.getParameter("description");
        if (!desc.isEmpty()) {
            object.setDescription(desc);
        }
        return object;
    }

    @Override
    protected void setAdditionalParameters(HttpServletRequest request) {
        // Exercise does not require additional parameters
    }
	
	
	
}
