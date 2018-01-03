<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
	<div class="col-lg-2">
	    <div class="form-group">
	        <form class="form-inline" method="post">
	            <input type="hidden" name="actionType" value="changeShowNumber" />
	            <label>Show</label>
	            <select class="form-control input-sm" onchange='this.form.submit();' name="show">
	                 <c:set var="pages" value="5,10,15,25,50"/>
	              <c:forEach items="${pages}" var="i">
	                   <option
	                   <c:if test="${i eq itemsOnPage}">selected="selected"</c:if>
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