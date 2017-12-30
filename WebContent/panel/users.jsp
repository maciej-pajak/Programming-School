<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:navWrapper title="Admin">
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
	                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Create new user</a>
	                    </h4>
	                </div>
	                <div id="collapseOne" class="panel-collapse collapse">
	                    <div class="panel-body">
	                        <form role="form" method="post">
	                           <input type="hidden" name="formType" value="new" />
	                            <div class="form-group">
	                                <label>Group name</label>
	                                <input class="form-control" name="name">
	                            </div>
	                            <button type="submit" class="btn btn-success btn-sm">Create</button>
	                       </form>
	                       
	                    </div>
	                </div>
	            </div>
               
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Email</th>
                            <th>Group</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr class="odd gradeX">
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>${user.getGroupId()}</td>
                                <td>
                                    <a type="button" class="btn btn-primary btn-xs" href="?edit=${user.id}">Edit</a>
                                    <a type="button" class="btn btn-danger btn-xs" href="?delete=${user.id}#delete">Delete</a>
                                </td>
                            </tr>
                            <c:choose>
	                            <c:when test="${param.edit eq user.id}">
	                               <tr class="info">
	                                    <td colspan="3">
	                                       <form role="form" method="post">
	                                           <input type="hidden" name="formType" value="edit" />
	                                           <input type="hidden" name="userId" value="${user.id}" />
	                                           <div class="form-group">
	                                                <label>User name:</label>
	                                                <input class="form-control" name="newName" placeholder="${user.username}">
	                                           </div>
	                                           <a type="button" class="btn btn-default btn-outline btn-sm" href="?">Discard changes</a>
	                                           <button type="submit" class="btn btn-success btn-sm">Save changes</button>
	                                       </form>
	                                    </td>
	                                </tr>
	                            </c:when>
	                            <c:when test="${param.delete eq user.id}">
	                               <tr class="danger">
                                        <td colspan="3">
                                           <form role="form" method="post">
                                               <input type="hidden" name="formType" value="delete" />
                                               <input type="hidden" name="userId" value="${user.id}" />
                                               <h4 id="delete">Are you sure you want to delete group: <strong>${user.username}</strong>?</h4>
                                               <a type="button" class="btn btn-default btn-outline btn-sm" href="?show=${show}">Cancel</a>
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
        <div class="row">
            <div class="col-lg-2">
                <div class="form-group">
                    <form class="form-inline" method="post">
                        <input type="hidden" name="formType" value="showPages" />
			            <label>Show</label>
			            <select class="form-control input-sm" onchange='this.form.submit();' name="show">
			                 <c:set var="pages" value="5,10,15,25,50"/>
			               <c:forEach items="${pages}" var="i">
                                <option
                                <c:if test="${i eq cookie.itemsOnPage.value}">selected="selected"</c:if>
                                >${i}</option>
                           </c:forEach>
			            </select>
		            </form>
		        </div>
		        
            </div>
            <div class="col-lg-10">
                <ul class="pagination pagination-sm" style="margin:0px;">
                    <li><a href="?page=${currentPage - 1}">Previous</a></li>
                    <c:forEach var="page" begin = "1" end="${numberOfPages}">
                        <li 
                        <c:if test="${page eq currentPage}">
                            class="active"
                        </c:if>
                        ><a href="?page=${page}">${page}</a></li>
                    </c:forEach>

                  <li><a href="?page=${currentPage + 1}">Next</a></li>
                </ul>
                </div>
        </div> 
    </jsp:body>
</t:navWrapper>