<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ tag description="Simple Wrapper Tag" pageEncoding="UTF-8" %>
<%@ attribute name="dataType" required="true" %>
<%@ attribute name="newFormFields" fragment="true" required="true" %>
<%@ attribute name="editFormFields" fragment="true" required="true" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<t:pageWrapper title="Admin">
    <jsp:attribute name="adminActive">
        class="active"
    </jsp:attribute>
    <jsp:attribute name="adminMenu">
        <ul class="nav nav-second-level">
            <li>
                <a href="${pageContext.request.contextPath}/panel/groups">Groups</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/panel/users">Users</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/panel/exercises">Exercises</a>
            </li>
            <li>
	            <a href="${pageContext.request.contextPath}/panel/logout"><i class="fa fa-sign-out fa-fw"></i> Log out</a>
	        </li>
        </ul>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Manage ${dataType}s</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="?new">Create new ${dataType}</a>
                        </h4>
                    </div>
                    <c:if test="${newOpen}">
                        <div class="panel-body">
                            <form role="form" method="post">
                               <input type="hidden" name="actionType" value="new" />
                               
                               <jsp:invoke fragment="newFormFields"/>
                               
                                <a type="button" class="btn btn-default btn-outline btn-sm" href="?">Close</a>
                                <button type="submit" class="btn btn-success btn-sm">Create</button>
                           </form>
                        </div>
                        </c:if>
                    </div>
                </div>
               
            </div>

        <div class="row">
            <div class="col-lg-12">
                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                    <thead>
                        <tr>
                            <c:forEach items="${tableHeader}" var="row">
                                <th>
                                    <jsp:include page="/WEB-INF/fragments/sortForm.jsp">
                                        <jsp:param value="${row[0]}" name="label"/>
                                        <jsp:param value="${row[1]}" name="column"/>
                                    </jsp:include>
                                </th>
                            </c:forEach>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${tableBody}" var="row">
                            <tr class="odd gradeX">
                                <c:forEach items="${row.value}" var="cell">
                                    <td>${cell}</td>
                                </c:forEach>
                              
                                <td>
                                    <a type="button" class="btn btn-primary btn-xs" href="?page=${currentPage}&edit=${row.key}#edit">Edit</a>
                                    <a type="button" class="btn btn-danger btn-xs" href="?page=${currentPage}&delete=${row.key}#delete">Delete</a>
                                </td>
                            </tr>
                            <c:choose>
                                <c:when test="${param.edit eq row.key}">
                                   <tr class="info">
                                        <td colspan="${fn:length(row.value) + 1}" id="edit">
                                           <form role="form" method="post">
                                               <input type="hidden" name="actionType" value="edit" />
                                               <input type="hidden" name="itemId" value="${row.key}" />
                                               
                                               <jsp:invoke fragment="editFormFields"/>
                                               
                                               <a type="button" class="btn btn-default btn-outline btn-sm" href="?page=${currentPage}">Discard changes</a>
                                               <button type="submit" class="btn btn-success btn-sm">Save changes</button>
                                           </form>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:when test="${param.delete eq row.key}">
                                   <tr class="danger">
                                        <td colspan="4">
                                           <form role="form" method="post">
                                               <input type="hidden" name="actionType" value="delete" />
                                               <input type="hidden" name="itemId" value="${row.key}" />
                                               <h4 id="delete">Are you sure you want to delete ${dataType}: <strong>${row.value[0]}</strong>?</h4>
                                               <a type="button" class="btn btn-default btn-outline btn-sm" href="?page=${currentPage}">Cancel</a>
                                               <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                           </form>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <jsp:include page="/WEB-INF/fragments/pagination.jsp" />
    </jsp:body>
</t:pageWrapper>