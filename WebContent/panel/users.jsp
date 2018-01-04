<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:adminWrapper dataType="user">
	<jsp:attribute name="newFormFields">
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
    </jsp:attribute>
    <jsp:attribute name="editFormFields">
	    <div class="form-group">
	         <label>User name:</label>
	         <input class="form-control" name="name" placeholder="${tableBody[param.edit][0]}">
	    </div>
	    <div class="form-group">
	         <label>Email:</label>
	         <input class="form-control" name="email" placeholder="${tableBody[param.edit][1]}">
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
    </jsp:attribute>
</t:adminWrapper>