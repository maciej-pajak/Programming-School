package pl.coderslab.controller.admin;

import java.util.HashMap;
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
	
	protected void sendAdditionalItemsForEdit(HttpServletRequest request) {
	    GroupDao dao = new GroupDao();
        request.setAttribute("groups", dao.loadAll());
    }
	
	protected void mapAdditionalIdsToStrings(HttpServletRequest request, User[] usersOnPage) {
        GroupDao dao = new GroupDao();
        Map<Integer,String> groupsNames = new HashMap<>();
        for (User u : usersOnPage) {
            if ( !groupsNames.containsKey(u.getGroupId()) ) {
                groupsNames.put(u.getGroupId(), dao.loadById(u.getGroupId()).getName());
            }
        }
        request.setAttribute("groupsNames", groupsNames);
    }

    protected User createObject(HttpServletRequest request) {
        return new User(request.getParameter("name"), request.getParameter("email"), request.getParameter("pass"), Integer.parseInt(request.getParameter("groupId")));
    }

    protected User editObject(HttpServletRequest request, User object) {
        System.out.println("editing: " + object.getUsername());
        String username = request.getParameter("name");
        if (!username.isEmpty()) {          // TODO check if this works correctly
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
            System.out.println("id set: " + object.getId());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return object;
    }

    protected ColumnsEnumInterface getSortDefault() {
        return User.Column.USERNAME;
    }

    protected ColumnsEnumInterface getSortByColumn(String param) {  // TODO change to pass only enum 
        return Enum.valueOf(User.Column.class, param);
    }

    protected DaoInterface<User> getDao() {
        return new UserDao();
    }

}
