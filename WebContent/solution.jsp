<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:navWrapper title="Solution">
    <jsp:body>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Solution <small>to exercise "${exercise.title}" by ${user.username}</small></h1>
            </div>
        </div>
        <div class="row">
                <div class="col-lg-12">
                <div class="panel panel-default">
	                <div class="panel-heading">
	                    <h4 class="panel-title">
	                        <a data-toggle="collapse" href="#collapseOne">Solution details</a>
	                    </h4>
	                </div>
	                <div id="collapseOne" class="panel-collapse collapse">
	                    <div class="panel-body">
	                       <p>
			                   by: <strong>${user.username}</strong>
			               </p>
			               <p>
			                   created: <strong>${solution.created}</strong>
			               </p>
			               <p>
			                   updated: <strong>${solution.updated}</strong>
			               </p>
	                    </div>
	                </div>
	            </div>
	            <div class="panel panel-default">
	                <div class="panel-heading">
	                    <h4 class="panel-title">
	                        <a data-toggle="collapse" href="#collapseTwo">Exercise details</a>
	                    </h4>
	                </div>
	                <div id="collapseTwo" class="panel-collapse collapse">
	                    <div class="panel-body">
	                        <p><strong>${exercise.title}</strong></p>
	                        <p>${exercise.description}</p>
	                    </div>
	                </div>
	            </div>
	            <div class="panel panel-default">
	                <div class="panel-heading">
	                    <h4 class="panel-title">
	                        <a data-toggle="collapse" href="#collapseThree">Solution content</a>
	                    </h4>
	                </div>
	                <div id="collapseThree" class="panel-collapse collapse in">
	                    <div class="panel-body">
	                        <pre>${solution.description}</pre>
	                    </div>
	                </div>
	            </div>
                    
                </div>
            </div>
    </jsp:body>
</t:navWrapper>