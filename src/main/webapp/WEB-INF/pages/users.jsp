<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/style/style.css" />" rel="stylesheet">
    <meta charset="UTF-8">
    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.1.3/flatly/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.css">
    <style>
        body { background-color: #e9e9e9; }
        .container { margin: 1px auto; }
    </style>
    <title>Users</title>
</head>
<body>

<div style="text-align: center; position: absolute; z-index: 15; top: 40%; left: 50%; margin: -150px 0 0 -150px;">
    <h3>Lista Utilizatorilor:</h3>
    <div class="container">
        <div class="panel-body">
            <label>X - Pentru a sterge utilizator</label>
            <c:forEach items="${userList}" var="user">
                <p class="hea" style="cursor: pointer" onclick=getUserById(${user.id})>
                        ${user.username}
                        ${user.status}
                    <a href="<c:url value="/user/delete-by-id/${user.id}" />">X</a>
                </p>

            </c:forEach>
            <%--<blockquote class="blockquote">--%>
                <%--<p id="showUserInfo" class="mb-0 text-danger"></p>--%>
            <%--</blockquote>--%>
        </div>
    </div>
    <a href="<c:url value="/logout" />">Iesire</a>
</div>
<%--<input type="button" value="JSON example" onclick="getAllUsersAsJson()" class="btn-danger btn-lg"/>--%>
<%--<div id="users"></div>--%>
</body>
</html>
