<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*"  %>
<%@ page import="fmi.usm.md.mvc.service.UserService" %>
<%@ page import="fmi.usm.md.mvc.model.User" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="java.util.Enumeration" %>
<html>
<head>

    <title>Orarul grupei</title>
    <link rel="stylesheet" href="../resources/style/style.css">
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
</head>
<body>
<form:form method="POST" action="${pageContext.servletContext.contextPath}/save-json/${groupName}/${scheduleType}" modelAttribute="json">

<div class="container" align="center">
    <h1>Grupa </h1><h1 class="nameGroup">${groupName}</h1>
    <h1 hidden class="typeSchedule">${scheduleType}</h1>
    <div id="header">
        Daca oraru inca nu a fost creat va fi aratat exemplul modificati-l si salvati-l
	<br>
	Simbolul "-" este folosit daca nu conteaza paritatea sau subgrupa
    </div>


    <div align="center" id='button_container' class='col-md-3' style="width: 250px">
        <button id='json_to_table_btn' type="button" class="btn btn-default btn-lg btn-block">Arata Orar</button>
    </div>

    <div class="row mt-3">
        <div id='table_container' class='col-md-12'></div>
    </div>

    <div id="toHeader" align="center" style="color:#006d16;">
        Transformati in json apoi salvati schimbarile!
    </div>

    <div align="center" id='button_container' class='col-md-3' style="width: 250px">
        <button id='table_to_json_btn' type="button" class="btn btn-default btn-lg btn-block">Transforma in JSON</button>
    </div>


    <label for='result_container'>Rezultatul:</label>
    <textarea name="json" id='result_container' class='col-md-12'></textarea>

    <%
        String userPrivilege = request.getAttribute("userPrivilege").toString();
        if (userPrivilege.equals("ADMIN") || userPrivilege.equals("TEACHER")) {
    %>
    <button type="submit" class="btn">Salveaza schimbarile</button>
    <%
    } else {
    %>
    <p>Nu aveti drepturi pentru a salva schimbarile</p>
    <%
        }
    %>



    </form:form>


    <div align="center">
        <a href="<c:url value="/logout" />">Iesire</a>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.contextMenu.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.1/jquery.ui.position.js"></script>

    <script src="<c:url value="/resources/js/jsoneditor.js" />"></script>
    <script>
        $(document).ready(function () {
            jsonEditorInit('table_container', 'Textarea1', 'result_container', 'json_to_table_btn', 'table_to_json_btn');
        });
    </script>

</div>

</body>
</html>
