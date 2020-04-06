<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" href="../resources/style/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Hello page</title>
</head>
<body>

<form:form action="/show-users" method="POST">
    <a href="/show-users/FEMALE">Show females</a>
    <a href="/show-users/MALE">Show males</a>
    <br>
</form:form>

<form:form action="/show-users/by-gender" method="GET" style="border: none;">
    <input type="radio" name="gender" value="FEMALE"> FEMALE
    <br>
    <input type="radio" name="gender" value="MALE"> MALE
    <br>
    <input type = "submit" value="Submit">
</form:form>



<form:form action="/show-users" method="POST" style="border: none;">
    <label>
        <input type="text" name="status">
    </label>
    <input type="submit" value="Take status and show users" class="btn-danger"/>
    <p style="color:red">${error}</p>
</form:form>

<c:forEach items="${userList}" var="user">
    <p>${user.username} - ${user.status}</p>
</c:forEach>

<a href="/show-users">Show all users page</a>
</body>
</html>