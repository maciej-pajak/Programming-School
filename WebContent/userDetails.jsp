<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:navWrapper title="Groups">
    <jsp:body>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">User details</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <p>username: <strong>${user.username}</strong></p>
                <p>email: <strong>${user.email}</strong></p>
            </div>
        </div>
        <div class="row">
           <div class="col-lg-12">
               <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                   <thead>
                       <tr>
                           <th>Exercise</th>
                           <th>Submission date</th>
                           <th>Action</th>
                       </tr>
                   </thead>
                   <tbody>
                       <c:forEach items="${solutions}" var="solution">
                           <tr class="odd gradeX">
                               <td>${exercisesMap.get(solution.exerciseId).title}</td>
                               <td>${solution.updated}</td>
                               <td><a href='<c:url value="solution?id=${solution.id}"/>'>Show solution</a></td>
                           </tr>
                       </c:forEach>
                   </tbody>
               </table>
           </div>
        </div>
    </jsp:body>
</t:navWrapper>