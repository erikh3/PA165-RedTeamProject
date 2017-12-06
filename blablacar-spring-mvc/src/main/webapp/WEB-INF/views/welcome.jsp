<%@ page language="java" contentType="text/html; ISO-8859-1" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/4/17
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>BlablaCar Welcome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/welcome.css" type="text/css">
</head>
<body>
    <c:out value="${myWelcomeMessage}"></c:out>
</body>
</html>
