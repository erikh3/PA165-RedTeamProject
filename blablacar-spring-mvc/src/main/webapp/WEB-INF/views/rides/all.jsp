<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/4/17
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<fmt:message key = "page.welcome.title" var = "title"/>
<blablacar-tags:page-template title="${title}">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/ride.css"  crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">
        <ul class="rides-list">
        <c:forEach var="ride" items="rides">
            <li class="ride-item">
                <c:out value="${ride.id}"></c:out>
                <c:out value="${ride.departure}"></c:out>
                <c:out value="${ride.sourcePlace}"></c:out>
                <c:out value="${ride.destinationPlace}"></c:out>
                <c:out value="${ride.availableSeats}"></c:out>
                <c:out value="${ride.seatPrice}"></c:out>
                <form:form action="${pageContext.request.contextPath}/ride/addPassenger" id="join-ride" method="post">
                    <button type="submit" class="btn btn-primary">Join ride</button>
                </form:form>
            </li>
        </c:forEach>
        </ul>
    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>