<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/resources/style/style.css" />" rel="stylesheet">
    <meta charset="UTF-8">
    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.1.3/flatly/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.css">
</head>
<body>
<div align="center">
    <div style="width: 300px; height: 500px;">
        <form:form method="POST" action="${pageContext.request.contextPath}/register" modelAttribute="user" cssClass="frm">
            <div class="imgcontainer">
                <img src="<c:url value="/resources/pictures/usm_bottle_green_400x400.png" />" alt="Avatar" class="avatar">
            </div>
            <h3 align="center" style="color:#009b76;">Completati Campurile!</h3>

            <div class="container" align="left">
                <p style="color:red">${error}</p>
                <label>Nume Utilizator:</label>
                <input type="text" name="username" required="required" class="inp"/>

                <label>Mail:</label>
                <input type="text" name="mail" required="required" class="inp"/>

                <label>Nume Prenume:</label>
                <input type="text" name="familyname" required="required" class="inp"/>

                <label>Parola:</label>
                <input type="password" name="password" required="required" class="inp"/>

                <label style="margin-right: 20px">Genul:</label>
                <input type="radio" name="gender" value="FEMALE" required="required"> F
                <input type="radio" name="gender" value="MALE" required="required"> M

                <br>
                <label style="margin-right: 15px">Grupa:</label>
                <select name="groupName" id="groupName" required="required">
                    <c:forEach var="groups" items="${groupList}" >
                        <option value="${groups.name}">${groups.name}</option>
                    </c:forEach>
                </select>
                <br>
                <label style="margin-right: 10px">SubGrupa:</label>
                <input type="radio" name="subGroup" value="I" id="subGroup" required="required"> I (s)
                <input type="radio" name="subGroup" value="II" id="subGroup" required="required"> II (d)
                <input type="radio" name="subGroup" value="Fara" id="subGroup" required="required"> Fara
                <button type="submit" class="btn">Inregistreaza-ma</button>
            </div>


        </form:form>
        <div align="right">
             <a href="<c:url value="/logout" />">Iesire</a>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.ui.position.js"></script>
    </div>
</div>
</body>
</html>
