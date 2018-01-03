package pl.coderslab.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.Group;
import pl.coderslab.model.dao.GroupDao;
import pl.coderslab.model.standards.ColumnsEnumInterface;

@WebServlet("/panel/groups")
public class GroupAdmin extends AbstractServlet<Group> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    request = paginate(request, new GroupDao());
	    Group[] groupsOnPage = paginate(request, new GroupDao());
	    request.setAttribute("items", groupsOnPage);
	    System.out.println(getServletContext().getContextPath());
	    request.getRequestDispatcher("/panel/groups.jsp").forward(request, response);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupDao dao = new GroupDao();
        performFormAction(request, response, dao);
        
        response.sendRedirect(request.getContextPath() + getJspPath());
    }
	
	protected ColumnsEnumInterface getSortByColumn(String param) {
	    System.out.println(Enum.valueOf(Group.Column.class, param));
	    return Enum.valueOf(Group.Column.class, param);
	}

    @Override
    protected GroupDao getDao() {
        return new GroupDao();
    }
    
    @Override
    protected Group createObject(HttpServletRequest request) {
        return new Group(request.getParameter("name"));
    }

    @Override
    protected Group editObject(HttpServletRequest request, Group group) {
        return group.setName(request.getParameter("newName"));
    }

    @Override
    protected ColumnsEnumInterface getSortDefault() {
        return Group.Column.NAME;
    }

}
