package pl.coderslab.controller.admin;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DaoInterface;

public abstract class AbstractServlet<T> extends HttpServlet {
    
    private static final String ITEMS_ON_PAGE_ATTR_NAME = "itemsOnPage";
    private static final String TABLE_HEADER_ATTR_NAME = "tableHeader";
    private static final String TABLE_BODY_ATTR_NAME = "tableBody"; 
    private static final String CURRENT_PAGE_PARAMETER = "page";
    private static final String SORT_BY_PARAM_NAME = "sortBy";
    private static final String SORT_TYPE_PARAM_NAME = "sortType";
    private static final String ITEM_ID_PARAM = "itemId";
    
    protected abstract DaoInterface<T> getDao();
    protected abstract String[][] tableHeader();
    protected abstract Map<String, String[]> table(ColumnsEnumInterface sortBy, DaoInterface.SortType sortType, int itemsOnPage, int offset);
    protected abstract ColumnsEnumInterface getSortDefault();
    protected abstract ColumnsEnumInterface getSortByColumn(String param);
    protected abstract T createObject(HttpServletRequest request);
    protected abstract T editObject(HttpServletRequest request, T object);
    protected abstract void setAdditionalParameters(HttpServletRequest request);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // for pagination
        int itemsOnPage = getItemsOnPageCount(request);
        int numberOfPages = (int) Math.ceil((double) getDao().getCount() / itemsOnPage);
        int currentPage = getCurrentPage(request, numberOfPages);

        // for sorting
        HttpSession sess = request.getSession();
        ColumnsEnumInterface sortBy = (ColumnsEnumInterface) sess.getAttribute(getSortBySessAttrName());
        DaoInterface.SortType sortType = (DaoInterface.SortType) sess.getAttribute(getSortTypeSessAttrName());
        sortBy = (sortBy != null) ? sortBy : getSortDefault();
        sortType = (sortType != null) ? sortType : DaoInterface.SortType.ASC;
        
        // set attributes
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute(ITEMS_ON_PAGE_ATTR_NAME, itemsOnPage);
        request.setAttribute(TABLE_BODY_ATTR_NAME, table(sortBy, sortType, itemsOnPage, (currentPage - 1) * itemsOnPage) );
        request.setAttribute(TABLE_HEADER_ATTR_NAME, tableHeader());
        newOrEdit(request); // additional attributes for creating new or editing

        request.getRequestDispatcher(request.getServletPath() + ".jsp").forward(request, response);

    }
    
    private void newOrEdit(HttpServletRequest request) {
        if (request.getParameter("edit") != null || request.getParameter("new") != null) {
            setAdditionalParameters(request);
            if (request.getParameter("new") != null) {
                request.setAttribute("newOpen", true);
            } else {
                request.setAttribute("newOpen", false);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performFormAction(request, response);
        response.sendRedirect(getServletContext().getContextPath() + request.getServletPath());
    }
    
    private int getItemsOnPageCount(HttpServletRequest request) {
        int res;
        String itemsOnPageStr = getCookieValue(getItemsOnPageCookieName(), request);
        if (itemsOnPageStr != null) {
            res = Integer.parseInt(itemsOnPageStr);
        } else {
            res = Integer.parseInt(getServletContext().getInitParameter("items-on-page-default"));
        }
        return res;
    }
    
    private int getCurrentPage(HttpServletRequest request, int numberOfPages) {
        int res = 1;
        String currentPageStr = request.getParameter(CURRENT_PAGE_PARAMETER);
        if (currentPageStr != null) {
            int tmp = Integer.parseInt(currentPageStr);
            if (tmp > numberOfPages) {
                res = numberOfPages;
            } else if (tmp < 1) {
                res = 1;
            } else {
                res = tmp;
            }
        }
        return res;
    }
    
    protected void performFormAction(HttpServletRequest request, HttpServletResponse response) {
        DaoInterface<T> dao = getDao();
        switch (request.getParameter("actionType")) {
        case "new":
            T newObject = createObject(request);
            dao.saveToDb(newObject);
            break;
        case "edit":
            T editedObject = dao.loadById(Integer.parseInt(request.getParameter(ITEM_ID_PARAM)));
            dao.saveToDb( editObject(request, editedObject) );
            break;
        case "delete":
            T deletedObject = dao.loadById(Integer.parseInt(request.getParameter(ITEM_ID_PARAM)));
            dao.delete(deletedObject);
            break;
        case "changeShowNumber":
            Cookie c = new Cookie(getItemsOnPageCookieName(), request.getParameter("show"));    // TODO or session??
            response.addCookie(c);
            break;
        case "sort":
            saveSortAttributes(request);
            break;
        default:
        }
    }

    private void saveSortAttributes(HttpServletRequest request) { // TODO or maybe cookie
        try {
            ColumnsEnumInterface by = getSortByColumn(request.getParameter(SORT_BY_PARAM_NAME));
            DaoInterface.SortType type = DaoInterface.SortType.valueOf(request.getParameter(SORT_TYPE_PARAM_NAME));
            HttpSession sess = request.getSession();
            sess.setAttribute(getSortBySessAttrName(), by);
            sess.setAttribute(getSortTypeSessAttrName(), type);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    private final String getSortBySessAttrName() {
        return this.getClass().getSimpleName() + SORT_BY_PARAM_NAME;
    }
    
    private final String getSortTypeSessAttrName() {
        return this.getClass().getSimpleName() + SORT_TYPE_PARAM_NAME;
    }
    
    private final String getItemsOnPageCookieName() {
        return this.getClass().getSimpleName() + ITEMS_ON_PAGE_ATTR_NAME;
    }
    
    private static String getCookieValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String result = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    result = c.getValue();
                    break;
                }
            }
        }
        return result;
    }
    
}
