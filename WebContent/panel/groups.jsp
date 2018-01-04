<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:adminWrapper dataType="group">
    <jsp:attribute name="newFormFields">
        <div class="form-group">
            <label>Group name:</label>
            <input class="form-control" name="name">
       </div>
    </jsp:attribute>
    <jsp:attribute name="editFormFields">
        <div class="form-group">
             <label>Group name:</label>
             <input class="form-control" name="name" placeholder="${row.value[0]}">
        </div>
    </jsp:attribute>
</t:adminWrapper>