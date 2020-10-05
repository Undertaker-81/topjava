<%--
  Created by IntelliJ IDEA.
  User: taker
  Date: 05.10.2020
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>

    </tr>
    </thead>
    <tbody>


    <c:forEach items="${meals}" var="meal">

        <tr style= "${meal.excess ? 'color: red' : 'color: green' }">


            <td><javatime:format value="${meal.dateTime}" style="MS" /> </td>
            <td>${meal.description} </td>
            <td>${meal.calories} </td>


        </tr>

    </c:forEach>
    </tbody>
</table>
</body>
</html>
