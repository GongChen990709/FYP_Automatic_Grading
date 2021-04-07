<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="FYP19.Entities.Student" %><%--
  Created by IntelliJ IDEA.
  User: gongchen
  Date: 2021/3/31
  Time: 3:59 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>StudentShow</title>
</head>
<body>
    <h1>Show all Students</h1>
    <c:forEach var="student" items="${studentList}">
        ${student.getStudentName()}
    </c:forEach>
    ${msg}
</body>
</html>
