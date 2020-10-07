<%--
  Created by IntelliJ IDEA.
  User: taker
  Date: 06.10.2020
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<form method="POST" action='meals' >
    <table border="0" cellpadding="4">
        <tr>
            <td>id:</td>
            <td>${mealid}
                <form:hidden path="id"/></td>
        </tr>
          <!--  <input type="text" readonly="readonly" name="mealid"
                   value="<c:out value="${mealid}" />" /> -->
        <tr>
            <td>Date:</td>
            <td><input type="datetime-local"  name="date" /> </td>
        </tr>
        <tr>
            <td>Description</td>
            <td><input  type="text" name="description" /></td>
        </tr>
        <tr>
            <td>Calories</td>
            <td><input type="text" name="calories" />  </td>
        </tr>

      <!--  <label for="date">Date</label>  <input type="datetime-local"  name="date" id="date" />  <br>

        <label for="desc">Description</label> <input  type="text" name="description" id="desc"/> <br>

        <label for="calories">Date</label> <input type="text" name="calories" id="calories"/>  <br>

        <input type="submit" value="Submit" /> -->
    </table>
    <input type="submit" value="Save" />
</form>

</body>
</html>
