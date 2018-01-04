<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:adminWrapper dataType="exercise">
    <jsp:attribute name="newFormFields">
        <div class="form-group">
            <label>Exercise title:</label>
            <input class="form-control" name="title">
       </div>
       <div class="form-group">
            <label>Exercise description:</label>
            <textarea name="description" class="form-control" rows="8"></textarea>
       </div>
    </jsp:attribute>
    <jsp:attribute name="editFormFields">
        <div class="form-group">
             <label>Exercise name:</label>
             <input class="form-control" name="title" placeholder="${tableBody[param.edit][0]}">
        </div>
        <div class="form-group">
             <label>Exercise description:</label>
             <textarea name="description" class="form-control" rows="8">${tableBody[param.edit][1]}</textarea>
        </div>
    </jsp:attribute>
</t:adminWrapper>