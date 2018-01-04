package pl.coderslab.controller.admin;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import pl.coderslab.model.User;
import pl.coderslab.model.dao.GroupDao;
import pl.coderslab.model.dao.UserDao;
import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DaoInterface;

@WebServlet("/panel/users")
public class UserAdmin extends AbstractServlet<User> {
	private static final long serialVersionUID = 1L;
	
	protected Map<String, String[]> table(ColumnsEnumInterface sortBy, DaoInterface.SortType sortType, int limit, int offset) {
	    UserDao dao = new UserDao();
	    GroupDao gDao = new GroupDao();
	    User[] users = dao.loadSortedWithLimit(sortBy, sortType, limit, offset);
	    Map<String, String[]> table = new LinkedHashMap<>();
        for (int i = 0 ; i < users.length ; i++) {
            String[] row = new String[3];
            row[0] = users[i].getUsername();
            row[1] = users[i].getEmail();
            row[2] = gDao.loadById(users[i].getGroupId()).getName();
            table.put(Integer.toString(users[i].getId()), row);
        }
	    return table;
	}
	
	@Override
    protected String[][] tableHeader() {
        return new String[][] { {"User", "USERNAME"}, {"Email", "EMAIL"}, {"Group", "GROUPID"} };
    }
	
	protected void setAdditionalParameters(HttpServletRequest request) {
	    GroupDao dao = new GroupDao();
        request.setAttribute("groups", dao.loadAll());
    }

    protected User createObject(HttpServletRequest request) {
        return new User(request.getParameter("name"), request.getParameter("email"), request.getParameter("pass"), Integer.parseInt(request.getParameter("groupId")));
    }

    protected User editObject(HttpServletRequest request, User object) {
        String username = request.getParameter("name");
        if (!username.isEmpty()) {
            object.setUsername(username);
        }
        String email = request.getParameter("email");
        if (!email.isEmpty()) {
            object.setEmail(email);
        }
        String password = request.getParameter("pass");
        if (!password.isEmpty()) {
            object.setPassword(password);
        }
        try {
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            object.setGroupId(groupId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return object;
    }

    protected ColumnsEnumInterface getSortDefault() {
        return User.Column.USERNAME;
    }

    protected ColumnsEnumInterface getSortByColumn(String param) {
        return Enum.valueOf(User.Column.class, param);
    }

    protected DaoInterface<User> getDao() {
        return new UserDao();
    }
    
}
