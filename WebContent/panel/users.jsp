<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

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
        </ul>
    </jsp:attribute>
	<jsp:body>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Manage users</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
	                <div class="panel-heading">
	                    <h4 class="panel-title">
	                        <a href="?new">Create new user</a>
	                    </h4>
	                </div>
	                <c:if test="${newOpen}">
	                    <div class="panel-body">
	                        <form role="form" method="post">
	                           <input type="hidden" name="actionType" value="new" />
	                            <div class="form-group">
                                     <label>User name:</label>
                                     <input class="form-control" name="name">
                                </div>
                                <div class="form-group">
                                     <label>Email:</label>
                                     <input class="form-control" name="email">
                                </div>
                                <div class="form-group">
                                     <label>Password:</label>
                                     <input type="password" class="form-control" name="pass">
                                </div>
                                <div class="form-group">
                                     <label>Group</label>
                                     <select class="form-control" name="groupId">
                                         <c:forEach var="group" items="${groups}">
                                               <option value="${group.id}">${group.name}</option>
                                         </c:forEach>
                                     </select>
                                 </div>
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
                            <th>
	                            <jsp:include page="/WEB-INF/fragments/sortForm.jsp">
							        <jsp:param value="User" name="label"/>
                                    <jsp:param value="USERNAME" name="column"/>
							    </jsp:include>
                            </th>
                            <th>
                                <jsp:include page="/WEB-INF/fragments/sortForm.jsp">
                                    <jsp:param value="Email" name="label"/>
                                    <jsp:param value="EMAIL" name="column"/>
                                </jsp:include>
                            </th>
                            <th><form role="form" method="post">
                                    Group
                                    <input type="hidden" name="actionType" value="sort"/>
                                    <input type="hidden" name="sortBy" value="GROUPID"/>
                                    <button type="submit" class="btn btn-default btn-xs fa fa-sort-asc" name="sortType" value="ASC"></button>
                                    <button type="submit" class="btn btn-default btn-xs fa fa-sort-desc"name="sortType" value="DESC"></button>
                                </form>
                            </th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${items}" var="user">
                            <tr class="odd gradeX">
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>${groupsNames.get(user.groupId)}</td>
                                <td>
                                    <a type="button" class="btn btn-primary btn-xs" href="?page=${currentPage}&edit=${user.id}#edit">Edit</a>
                                    <a type="button" class="btn btn-danger btn-xs" href="?page=${currentPage}&delete=${user.id}#delete">Delete</a>
                                </td>
                            </tr>
                            <c:choose>
	                            <c:when test="${param.edit eq user.id}">
	                               <tr class="info">
	                                    <td colspan="4">
	                                       <form role="form" method="post">
	                                           <input type="hidden" name="actionType" value="edit" />
	                                           <input type="hidden" name="itemId" value="${user.id}" />
	                                           <div class="form-group" id="edit">
	                                                <label>User name:</label>
	                                                <input class="form-control" name="name" placeholder="${user.username}">
	                                           </div>
	                                           <div class="form-group">
                                                    <label>Email:</label>
                                                    <input class="form-control" name="email" placeholder="${user.email}">
                                               </div>
                                               <div class="form-group">
                                                    <label>New password:</label>
                                                    <input type="password" class="form-control" name="pass">
                                               </div>
                                               <div class="form-group">
		                                            <label>Group</label>
		                                            <select class="form-control" name="groupId">
		                                                <c:forEach var="group" items="${groups}">
		                                                      <option value="${group.id}">${group.name}</option>
		                                                </c:forEach>
		                                            </select>
		                                        </div>
	                                           <a type="button" class="btn btn-default btn-outline btn-sm" href="?page=${currentPage}">Discard changes</a>
	                                           <button type="submit" class="btn btn-success btn-sm">Save changes</button>
	                                       </form>
	                                    </td>
	                                </tr>
	                            </c:when>
	                            <c:when test="${param.delete eq user.id}">
	                               <tr class="danger">
                                        <td colspan="4">
                                           <form role="form" method="post">
                                               <input type="hidden" name="actionType" value="delete" />
                                               <input type="hidden" name="itemId" value="${user.id}" />
                                               <h4 id="delete">Are you sure you want to delete user: <strong>${user.username}</strong>?</h4>
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
        <%@ include file="/WEB-INF/fragments/pagination.jsp" %>
    </jsp:body>
</t:pageWrapper>