<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:navWrapper title="Homepage">
    <jsp:body>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Homepage</h1>
            </div>
        </div>
        <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Latest solutions
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>Task</th>
                                        <th>User</th>
                                        <th>Updated</th>
                                        <th>Details</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${latestSolutions}" var="solution">
	                                    <tr class="odd gradeX">
	                                        <td>${latestExercises.get(solution.exerciseId).title}</td>
	                                        <td>${latestUsers.get(solution.userId).username}</td>
	                                        <td>${solution.updated}</td>
	                                        <td><a href='<c:url value="solution?id=${solution.id}"/>'>Details</a></td>
	                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
    </jsp:body>
</t:navWrapper>