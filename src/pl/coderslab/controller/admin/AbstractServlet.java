package pl.coderslab.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DaoInterface;

public abstract class AbstractServlet<T> extends HttpServlet {
    
//    private static final long serialVersionUID = 1L;    // TODO ??? delete?

    protected abstract DaoInterface<T> getDao();
    
    private static final String ITEMS_ON_PAGE_COOKIE_NAME = "itemsOnPage";
    private static final String ITEMS_ON_PAGE_ATTRIBUTE = "items";
    private static final String CURRENT_PAGE_PARAMETER = "page";
    private static final String SORT_BY_PARAM_NAME = "sortBy";
    private static final String SORT_TYPE_PARAM_NAME = "sortType";
    private static final String ITEM_ID_PARAM = "itemId";
    private final String getSortBySessAttrName() {
        return this.getClass().getSimpleName() + SORT_BY_PARAM_NAME;
    }
    private final String getSortTypeSessAttrName() {
        return this.getClass().getSimpleName() + SORT_TYPE_PARAM_NAME;
    }
    private final String getItemsOnPageCookieName() {
        return this.getClass().getSimpleName() + ITEMS_ON_PAGE_COOKIE_NAME;
    }
    protected abstract void sendAdditionalItemsForEdit(HttpServletRequest request);
    protected abstract void mapAdditionalIdsToStrings(HttpServletRequest request, T[] itemsOnPage);

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        T[] itemsOnPage = paginate(request);
        request.setAttribute(ITEMS_ON_PAGE_ATTRIBUTE, itemsOnPage);
        if (request.getParameter("edit") != null || request.getParameter("new") != null) {
            sendAdditionalItemsForEdit(request);
            if (request.getParameter("new") != null) {
                request.setAttribute("newOpen", true);
            } else {
                request.setAttribute("newOpen", false);
            }
        }
        mapAdditionalIdsToStrings(request, itemsOnPage);
        
        try {
            request.getRequestDispatcher(request.getServletPath() + ".jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performFormAction(request, response);
        response.sendRedirect(getServletContext().getContextPath() + request.getServletPath());
    }
    
    protected T[] paginate(HttpServletRequest request) {
        int numberOfPages = 1;
        int currentPage = 1;
        int itemsOnPage;
        DaoInterface<T> dao = getDao();
        String itemsOnPageStr = getCookieValue(ITEMS_ON_PAGE_COOKIE_NAME, request);     // TODO this cookie needs to be different for every subsite
        if (itemsOnPageStr != null) {
            itemsOnPage = Integer.parseInt(itemsOnPageStr);
        } else {
            itemsOnPage = Integer.parseInt(getServletContext().getInitParameter("items-on-page-default"));
        }
        numberOfPages = (int) Math.ceil((double) dao.getCount() / itemsOnPage);
        String currentPageStr = request.getParameter(CURRENT_PAGE_PARAMETER);
        if (currentPageStr != null) {
            int tmp = Integer.parseInt(currentPageStr);
            if (tmp > numberOfPages) {
                currentPage = numberOfPages;
            } else if (tmp < 1) {
                currentPage = 1;
            } else {
                currentPage = tmp;
            }
        }
        
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("itemsOnPage", itemsOnPage);
        
        HttpSession sess = request.getSession();
        
        ColumnsEnumInterface sortBy = (ColumnsEnumInterface) sess.getAttribute(getSortBySessAttrName());
        DaoInterface.SortType sortType = (DaoInterface.SortType) sess.getAttribute(getSortTypeSessAttrName());
        sortBy = (sortBy != null) ? sortBy : getSortDefault();
        sortType = (sortType != null) ? sortType : DaoInterface.SortType.ASC;
        return dao.loadSortedWithLimit(sortBy, sortType, itemsOnPage, (currentPage - 1) * itemsOnPage);
    }
    protected abstract ColumnsEnumInterface getSortDefault();
    
    protected abstract T createObject(HttpServletRequest request);
    protected abstract T editObject(HttpServletRequest request, T object);
    
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
    protected abstract ColumnsEnumInterface getSortByColumn(String param);

    private void saveSortAttributes(HttpServletRequest request) {
        try {
            ColumnsEnumInterface by = getSortByColumn(request.getParameter(SORT_BY_PARAM_NAME));
            DaoInterface.SortType type = DaoInterface.SortType.valueOf(request.getParameter(SORT_TYPE_PARAM_NAME));
            HttpSession sess = request.getSession();    // TODO or maybe cookie
            sess.setAttribute(getSortBySessAttrName(), by);
            sess.setAttribute(getSortTypeSessAttrName(), type);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
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
