package pl.coderslab.controller.admin;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import pl.coderslab.model.Group;
import pl.coderslab.model.dao.GroupDao;
import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DaoInterface;
import pl.coderslab.model.standards.DaoInterface.SortType;

@WebServlet("/panel/groups")
public class GroupAdmin extends AbstractServlet<Group> {
	private static final long serialVersionUID = 1L;

    @Override
    protected DaoInterface<Group> getDao() {
        return new GroupDao();
    }

    @Override
    protected String[][] tableHeader() {
        return new String[][] { {"Group", "NAME"} };
    }

    @Override
    protected Map<Integer, String[]> table(ColumnsEnumInterface sortBy, SortType sortType, int limit, int offset) {
        GroupDao dao = new GroupDao();
        Group[] groups = dao.loadSortedWithLimit(sortBy, sortType, limit, offset);
        Map<Integer, String[]> table = new LinkedHashMap<>();
        for (int i = 0 ; i < groups.length ; i++) {
            String[] row = new String[1];
            row[0] = groups[i].getName();
            table.put(groups[i].getId(), row);
        }
        return table;
    }
    
    @Override
    protected ColumnsEnumInterface getSortDefault() {
        return Group.Column.NAME;
    }

    @Override
    protected ColumnsEnumInterface getSortByColumn(String param) {
        return Enum.valueOf(Group.Column.class, param);
    }

    @Override
    protected Group createObject(HttpServletRequest request) {
        return new Group(request.getParameter("name"));
    }

    @Override
    protected Group editObject(HttpServletRequest request, Group object) {
        String name = request.getParameter("name");
        if (!name.isEmpty()) {
            object.setName(name);
        }
        return object;
    }

    @Override
    protected void setAdditionalParameters(HttpServletRequest request) {
        // Group doesn't need any additional parameters
    }



}
