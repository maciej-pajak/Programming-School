<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form role="form" method="post">
    ${param.label}
    <input type="hidden" name="actionType" value="sort"/>
    <input type="hidden" name="sortBy" value="${param.column}"/>
    <button type="submit" class="btn btn-default btn-xs fa fa-sort-asc" name="sortType" value="ASC"></button>
    <button type="submit" class="btn btn-default btn-xs fa fa-sort-desc"name="sortType" value="DESC"></button>
</form>