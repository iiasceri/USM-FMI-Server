<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: pcovaliov
  Date: 11/27/2018
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../resources/style/style.css">
</head>
<body>
<h1>Hello
    <security:authorize access="isAuthenticated()">
        <security:authentication property="principal.username" />
    </security:authorize>!
</h1>
<form:form method="POST" action="/task/add-task" modelAttribute="task" cssClass="frm">
    <div class="container" align="left">
        <label>Description</label>
        <input type="text" name="description" required="required" class="inp"/>

        <label>Due date</label>
        <input type="date" name="dueDate" required="required" class="inp"/>

        <label>Comment</label>
        <input type="text" name="comment" required="required" class="inp"/>

        <button type="submit" class="btn">Create Task</button>
    </div>
</form:form>

<div id="test" class="panel panel-default">
    <div class="panel-body">
        <c:forEach items="${taskList}" var="task">
           <p class="hea" style="cursor: pointer"> ${task.description}
               <a href="/task/delete-by-id/${task.id}">X</a>
           </p>
        </c:forEach>
    </div>
</div>

 <a href="<c:url value="/logout" />">Logout</a>

</body>
</html>
