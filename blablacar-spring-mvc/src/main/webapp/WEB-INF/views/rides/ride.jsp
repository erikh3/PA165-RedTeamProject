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
        <div class="jumbotron">
            <h2>Driver: <c:out value="${rideDTO.driver.name}"></c:out></h2>
            <h2>Date: <fmt:formatDate value="${rideDTO.departure}" pattern="dd.MM.yyyy" /></h2>
            <h2>From: <c:out value="${rideDTO.sourcePlace.name}"></c:out></h2>
            <h2>To: <c:out value="${rideDTO.destinationPlace.name}"></c:out></h2>
            <h2>Seat Price: <c:out value="${rideDTO.seatPrice}"></c:out></h2>
            <h2>Available Seats: <c:out value="${rideDTO.availableSeats}"></c:out></h2>
        </div>
        <form:form action="${pageContext.request.contextPath}/comment/new" id="join-ride" method="get">
            <button type="submit" class="btn btn-default" name="rideId" value="${rideDTO.id}">Add comment</button>
        </form:form>
        <ul class="list-group">
            <c:forEach items="${rideDTO.comments}" var="comment">
                <li class="list-group-item">
                    <div class="comment-holder">
                        <h3><c:out value="${comment.created}" /></h3>
                        <pre>
                            <c:out value="${comment.text}" />
                        </pre>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </jsp:attribute>





    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>