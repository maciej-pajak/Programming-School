<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

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
                <h1 class="page-header">Admin panel</h1>
            </div>
        </div>
        <div class="row">
                <div class="col-lg-12">
                    
                      
                </div>
            </div>
    </jsp:body>
</t:pageWrapper>