<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/4/17
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<blablacar-tags:page-template title="Ride info">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/ride.css"
              crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/style.css"
              crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">
        <div class="jumbotron row">
            <div class="col-xs-6 background background-${rideDTO.sourcePlace.name} no-padding">
                <div class="green-shader text-center c-white">
                    <h2>From: <c:out value="${rideDTO.sourcePlace.name}"></c:out></h2>
                    <h2>Driver: <c:out value="${rideDTO.driver.name}"></c:out></h2>
                    <h2>Date: <fmt:formatDate value="${rideDTO.departure}" pattern="dd.MM.yyyy"/></h2>
                </div>
            </div>
            <div class="col-xs-6 background background-${rideDTO.destinationPlace.name} no-padding">
                <div class="green-shader text-center c-white">
                    <h2>To: <c:out value="${rideDTO.destinationPlace.name}"></c:out></h2>
                    <h2>Seat Price: <c:out value="${rideDTO.seatPrice}"></c:out></h2>
                    <h2>Available Seats: <c:out value="${rideDTO.availableSeats}"></c:out></h2>
                </div>
            </div>
        </div>

        <div class="jumbotron" style="margin-bottom: 1%">
            <h3>Last comments on this ride</h3>
            <c:if test="${fn:length(rideDTO.comments) eq 0}">
                <p>No comments</p>
            </c:if>
            <ul class="list-group">
                <c:forEach items="${rideDTO.comments}" var="comment">
                    <li class="list-group-item">
                        <div class="comment-holder row">
                            <div class="col-xs-10">
                                <c:out value="${comment.text}"/>
                            </div>
                            <div class="col-xs-2">
                                <span class="badge float-right"><fmt:formatDate value="${comment.created}" pattern="dd.MM.yyyy"/></span>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <form:form action="${pageContext.request.contextPath}/comment/new" id="join-ride" method="get">
            <button type="submit" class="btn btn-default pull-right" name="rideId" style="margin-bottom: 5%" value="${rideDTO.id}">Add comment</button>
        </form:form>
    </jsp:attribute>


    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>